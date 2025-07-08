package com.example.taskmanagerapi.controller

import com.example.taskmanagerapi.models.ApiResponse
import com.example.taskmanagerapi.models.UsersDTO
import com.example.taskmanagerapi.models.UsersLoginDTO
import com.example.taskmanagerapi.models.UsersRegisterDTO
import com.example.taskmanagerapi.service.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UsersController (
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody userRegisterRequest: UsersRegisterDTO
    ) : ResponseEntity<ApiResponse<UsersDTO>> {
        return try {
            val registeredUser = userService.registerUser(
                email = userRegisterRequest.email,
                password = userRegisterRequest.password,
            )
            ResponseEntity.ok(ApiResponse(registeredUser))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = "Error message: ${e.message}",
                    data = null
                )
            )
        }
    }

    @PostMapping("/login")
    fun login(
        @RequestBody userLoginRequest: UsersLoginDTO
    ): ResponseEntity<ApiResponse<UsersDTO>> {
        return try {
            val loggedInUser = userService.loginUser(
                email = userLoginRequest.email,
                password = userLoginRequest.password,
            )
            ResponseEntity.ok(ApiResponse(loggedInUser))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = "Error message: ${e.message}",
                    data = null
                )
            )
        }
    }
}