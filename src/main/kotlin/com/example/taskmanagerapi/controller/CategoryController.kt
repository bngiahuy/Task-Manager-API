package com.example.taskmanagerapi.controller

import com.example.taskmanagerapi.models.ApiResponse
import com.example.taskmanagerapi.models.CategoryDTO
import com.example.taskmanagerapi.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController (
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getAllCategories(
        @RequestParam(required = true) userId: Long,
        @RequestParam(required = true, defaultValue = "0") page: Int = 0,
        @RequestParam(required = true, defaultValue = "10") size: Int = 10
    ): ResponseEntity<ApiResponse<List<CategoryDTO>>> {
        return try {
            val categories = categoryService.getAllCategories(userId, page, size)
            ResponseEntity.ok(ApiResponse(categories))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = "Error fetching categories: ${e.message}",
                    data = null
                )
            )
        }
    }

    @GetMapping("/{categoryId}")
    fun getCategoryById(
        @PathVariable(required = true) categoryId: Long,
        @RequestParam(required = true) userId: Long
    ): ResponseEntity<ApiResponse<CategoryDTO>> {
        return try {
            val category = categoryService.getCategoryById(categoryId, userId)
            ResponseEntity.ok(ApiResponse(category))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(
                ApiResponse(
                    success = false,
                    message = "Error fetching category: ${e.message}",
                    data = null
                )
            )
        }
    }

    @GetMapping("/create")
    fun createCategory(
        @RequestParam(required = true) name: String,
        @RequestParam(required = true) userId: Long
    ): ResponseEntity<ApiResponse<CategoryDTO>> {
        return try {
            val newCategory = categoryService.createCategory(name, userId)
            ResponseEntity.ok(ApiResponse(newCategory))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = "Error creating category: ${e.message}",
                    data = null
                )
            )
        }
    }

    @PutMapping("/{categoryId}")
    fun updateCategory(
        @PathVariable(required = true) categoryId: Long,
        @RequestParam(required = true) userId: Long,
        @RequestParam(required = false) name: String?,
    ): ResponseEntity<ApiResponse<CategoryDTO>> {
        return try {
            val updatedCategory = categoryService.updateCategory(categoryId, userId, name)
            ResponseEntity.ok(ApiResponse(updatedCategory))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
        @PathVariable(required = true) categoryId: Long,
        @RequestParam(required = true) userId: Long
    ): ResponseEntity<ApiResponse<Void>> {
        return try {
            categoryService.deleteCategory(categoryId, userId)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}