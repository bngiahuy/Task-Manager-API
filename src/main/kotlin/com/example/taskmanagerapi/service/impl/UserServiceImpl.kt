package com.example.taskmanagerapi.service.impl

import com.example.taskmanagerapi.entity.Users
import com.example.taskmanagerapi.models.UsersDTO
import com.example.taskmanagerapi.repository.UserRepository
import com.example.taskmanagerapi.service.JwtService
import com.example.taskmanagerapi.service.UserService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : UserService {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl::class.java)
    }
    override fun registerUser(email: String, password: String): UsersDTO {
        try {
            // Check if the user already exists
            if (userRepository.existsByEmail(email)) {
                throw IllegalArgumentException("User with email $email already exists")
            }
            val passwordHash = passwordEncoder.encode(password)
            val user = Users(
                id = 0,
                email = email,
                role = Users.UserRole.USER,
                passwordHash = passwordHash,
            )
            val savedUser = userRepository.save(user)
            logger.info("User registered successfully with email: $email")
            return savedUser.toDto()
        } catch (e: Exception) {
            throw RuntimeException("Error registering user: ${e.message}", e)
        }

    }

    override fun loginUser(email: String, password: String): UsersDTO {
        try {
            val user = userRepository.findByEmail(email)
                ?: throw IllegalArgumentException("User with email $email not found")
            if (!passwordEncoder.matches(password, user.passwordHash)) {
                throw IllegalArgumentException("Invalid password for user with email $email")
            }
            // Generate JWT token
            val token = jwtService.generateToken(user)
            val result = user.toDto()
            result.token = token
            logger.info("User logged in successfully with email: $email")

            return result
        } catch (e: Exception) {
            logger.error("Error logging in user with email $email: ${e.message}", e)
            throw RuntimeException("Error logging in user: ${e.message}", e)
        }
    }

    override fun getUserByEmail(email: String): UsersDTO {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User with email $email not found")
        return user.toDto()
    }

    override fun getUserById(id: Long): UsersDTO {
        val user = userRepository.findById(id).orElseThrow {
            IllegalArgumentException("User with ID $id not found")
        }
        return user.toDto()
    }


}