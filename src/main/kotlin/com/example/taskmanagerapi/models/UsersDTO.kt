package com.example.taskmanagerapi.models

data class UsersDTO(
    val id: Long = 0,
    val email: String,
    val role: String = "USER"
)

data class UsersLoginDTO(
    val email: String,
    val password: String
)

data class UsersRegisterDTO(
    val email: String,
    val password: String,
    val role: String = "USER"
)