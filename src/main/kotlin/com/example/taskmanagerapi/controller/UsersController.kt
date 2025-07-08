package com.example.taskmanagerapi.controller

import com.example.taskmanagerapi.models.ApiResponse
import com.example.taskmanagerapi.models.UsersDTO
import com.example.taskmanagerapi.models.UsersLoginDTO
import com.example.taskmanagerapi.models.UsersRegisterDTO
import com.example.taskmanagerapi.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    @Operation(
        summary = "Register a new user",
        description = "Register a new user with email and password."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "User registered successfully"),
            SwaggerApiResponse(responseCode = "400", description = "Error registering user")
        ]
    )
    @PostMapping("/register")
    fun register(
        @Parameter(description = "User registration data", required = true)
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

    @Operation(
        summary = "Login user",
        description = "Authenticate user with email and password."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "User logged in successfully"),
            SwaggerApiResponse(responseCode = "400", description = "Error logging in")
        ]
    )
    @PostMapping("/login")
    fun login(
        @Parameter(description = "User login data", required = true)
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