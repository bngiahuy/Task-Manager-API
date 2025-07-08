package com.example.taskmanagerapi.controller

import com.example.taskmanagerapi.models.ApiResponse
import com.example.taskmanagerapi.models.CategoryDTO
import com.example.taskmanagerapi.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
class CategoryController (
    private val categoryService: CategoryService
) {
    @Operation(
        summary = "Get all categories",
        description = "Retrieve all categories for a specific user with pagination."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
            SwaggerApiResponse(responseCode = "400", description = "Invalid request")
        ]
    )
    @GetMapping
    fun getAllCategories(
        @Parameter(description = "User ID", required = true)
        @RequestParam(required = true) userId: Long,
        @Parameter(description = "Page number", required = false)
        @RequestParam(required = true, defaultValue = "0") page: Int = 0,
        @Parameter(description = "Page size", required = false)
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

    @Operation(
        summary = "Get category by ID",
        description = "Retrieve a category by its ID for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Successfully retrieved category"),
            SwaggerApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping("/{categoryId}")
    fun getCategoryById(
        @Parameter(description = "Category ID", required = true)
        @PathVariable(required = true) categoryId: Long,
        @Parameter(description = "User ID", required = true)
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

    @Operation(
        summary = "Create a new category",
        description = "Create a new category for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Category created successfully"),
            SwaggerApiResponse(responseCode = "400", description = "Error creating category")
        ]
    )
    @GetMapping("/create")
    fun createCategory(
        @Parameter(description = "Category name", required = true)
        @RequestParam(required = true) name: String,
        @Parameter(description = "User ID", required = true)
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

    @Operation(
        summary = "Update a category",
        description = "Update the name of a category for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Category updated successfully"),
            SwaggerApiResponse(responseCode = "404", description = "Category not found")
        ]
    )
    @PutMapping("/{categoryId}")
    fun updateCategory(
        @Parameter(description = "Category ID", required = true)
        @PathVariable(required = true) categoryId: Long,
        @Parameter(description = "User ID", required = true)
        @RequestParam(required = true) userId: Long,
        @Parameter(description = "New category name", required = false)
        @RequestParam(required = false) name: String?,
    ): ResponseEntity<ApiResponse<CategoryDTO>> {
        return try {
            val updatedCategory = categoryService.updateCategory(categoryId, userId, name)
            ResponseEntity.ok(ApiResponse(updatedCategory))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Delete a category",
        description = "Delete a category by its ID for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "204", description = "Category deleted successfully"),
            SwaggerApiResponse(responseCode = "404", description = "Category not found")
        ]
    )
    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
        @Parameter(description = "Category ID", required = true)
        @PathVariable(required = true) categoryId: Long,
        @Parameter(description = "User ID", required = true)
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