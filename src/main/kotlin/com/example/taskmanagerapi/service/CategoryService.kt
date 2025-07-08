package com.example.taskmanagerapi.service

import com.example.taskmanagerapi.models.CategoryDTO

interface CategoryService {
    fun createCategory(name: String, userId: Long): CategoryDTO
    fun getCategoryById(categoryId: Long, userId: Long): CategoryDTO
    fun updateCategory(categoryId: Long, userId: Long, name: String?): CategoryDTO
    fun deleteCategory(categoryId: Long, userId: Long)
    fun getAllCategories(userId: Long, page: Int, size: Int): List<CategoryDTO>
}