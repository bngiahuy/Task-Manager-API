package com.example.taskmanagerapi.models

data class TaskDTO(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val status: String = "PENDING",
    val categoryId: Long? = null,
    val userId: Long,
    val dueDate: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
