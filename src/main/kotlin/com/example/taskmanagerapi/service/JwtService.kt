package com.example.taskmanagerapi.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService (
    @Value("\${jwt.secret.key}") private val secretKey: String = SECRET_KEY,
) {

    companion object {
        private const val EXPIRATION_DEFAULT = 1000 * 60 * 60      // 1 hour
        private const val EXPIRATION_RESET = 1000 * 60 * 2         // 2 minutes
        private const val SECRET_KEY =
            "9dc3f88bdc6bda759fe938e538794a661ced711c543ee76b7575b0b1dd6ef78df7a9956ce282bde0093559d94df813aac0bcf89a9335f5706f0989247752996c"
    }

    fun extractEmail(jwt: String): String? {
        try {
            return extractClaim(jwt, Claims::getSubject)
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalArgumentException("Invalid JWT token")
        }
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(emptyMap(), userDetails)
    }

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String = Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.username)
        .issuedAt(Date(System.currentTimeMillis()))
        .expiration(Date(System.currentTimeMillis() + EXPIRATION_DEFAULT))
        .signWith(getSecretKey())
        .compact()

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T = claimsResolver(extractAllClaims(token))


    private fun extractAllClaims(token: String): Claims = Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .payload

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractEmail(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    private fun getSecretKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateResetToken(userDetails: UserDetails): String {
        return generateResetToken(emptyMap(), userDetails)
    }

    fun generateResetToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + EXPIRATION_RESET))
            .signWith(getSecretKey())
            .compact()
    }
}