package com.example.taskmanagerapi.models

data class CategoryDTO(
    val id: Long = 0,
    val name: String,
    val userId: Long?
)
