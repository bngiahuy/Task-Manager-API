package com.example.taskmanagerapi.config


import com.example.taskmanagerapi.models.ApiResponse
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtExpiredTokenExceptionHandler {
    @Throws(IOException::class)
    fun commence(request: HttpServletRequest, response: HttpServletResponse, ex: ExpiredJwtException) {
        response.apply {
            status = HttpServletResponse.SC_UNAUTHORIZED
            contentType = "application/json;charset=UTF-8"
            val res = ApiResponse(false, "Token Expired", null)
            JsonConverter.convertObjectToJson(res)?.let { writer.write(it) }
        }
    }
}