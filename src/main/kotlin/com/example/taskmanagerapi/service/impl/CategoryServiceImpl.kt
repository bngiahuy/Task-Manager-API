package com.example.taskmanagerapi.service.impl

import com.example.taskmanagerapi.entity.Category
import com.example.taskmanagerapi.models.CategoryDTO
import com.example.taskmanagerapi.repository.CategoryRepository
import com.example.taskmanagerapi.repository.UserRepository
import com.example.taskmanagerapi.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : CategoryService {
    override fun createCategory(name: String, userId: Long): CategoryDTO {
        try {
            val user = userRepository.findById(userId).orElseThrow {
                IllegalArgumentException("User with ID $userId not found")
            }
            val newCategory = categoryRepository.save(
                Category(
                    name = name,
                    user = user
                )
            )
            return newCategory.toDto()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getCategoryById(categoryId: Long, userId: Long): CategoryDTO {
        val category = categoryRepository.findByUserIdAndId(userId, categoryId)
            ?: throw IllegalArgumentException("Category with ID $categoryId not found for user $userId")
        return category.toDto()
    }

    override fun updateCategory(categoryId: Long, userId: Long, name: String?): CategoryDTO {
        val category = categoryRepository.findByUserIdAndId(userId, categoryId)
            ?: throw IllegalArgumentException("Category with ID $categoryId not found for user $userId")
        if (name != null) {
            category.name = name
        }
        val updatedCategory = categoryRepository.save(category)
        return updatedCategory.toDto()
    }

    override fun deleteCategory(categoryId: Long, userId: Long) {
        val category = categoryRepository.findByUserIdAndId(userId, categoryId)
            ?: throw IllegalArgumentException("Category with ID $categoryId not found for user $userId")

        categoryRepository.delete(category)
    }

    override fun getAllCategories(userId: Long, page: Int, size: Int): List<CategoryDTO> {
        val pageable = org.springframework.data.domain.PageRequest.of(page, size)
        val categories = categoryRepository.findByUserId(userId, pageable)
        return categories.map { it.toDto() }
    }
}