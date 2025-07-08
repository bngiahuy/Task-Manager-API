package com.example.taskmanagerapi.controller

import com.example.taskmanagerapi.models.ApiResponse
import com.example.taskmanagerapi.models.TaskDTO
import com.example.taskmanagerapi.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.security.SecurityRequirement

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/tasks")
class TaskController (
    private val taskService: TaskService,
) {
    @Operation(
        summary = "Create a new task",
        description = "Create a new task for a user in a specific category."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Task created successfully"),
            SwaggerApiResponse(responseCode = "400", description = "Error creating task")
        ]
    )
    @PostMapping("/create")
    fun createTask(
        @Parameter(description = "Task data", required = true)
        @RequestBody taskRequest: TaskDTO
    ): ResponseEntity<ApiResponse<TaskDTO>> {
        return try {
            val createdTask = taskService.createTask(
                title = taskRequest.title,
                description = taskRequest.description ?: "",
                status = taskRequest.status,
                dueDate = taskRequest.dueDate,
                userId = taskRequest.userId,
                categoryId = taskRequest.categoryId
            )
            ResponseEntity.ok(ApiResponse(createdTask))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = "Error creating task: ${e.message}",
                    data = TaskDTO(
                        title = taskRequest.title,
                        description = taskRequest.description,
                        status = taskRequest.status,
                        dueDate = taskRequest.dueDate,
                        userId = taskRequest.userId,
                        categoryId = taskRequest.categoryId,
                        createdAt = null,
                        updatedAt = null
                    )
                )
            )
        }
    }

    @Operation(
        summary = "Get all tasks",
        description = "Retrieve all tasks for a specific user with pagination."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Successfully retrieved tasks"),
            SwaggerApiResponse(responseCode = "404", description = "Tasks not found")
        ]
    )
    @GetMapping
    fun getAllTasks(
        @Parameter(description = "User ID", required = true)
        @RequestParam(required = true) userId: Long,
        @Parameter(description = "Page number", required = false)
        @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "Page size", required = false)
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<List<TaskDTO>>> {
        return try {
            val tasks = taskService.getAllTasks(userId, page, size)
            ResponseEntity.ok(ApiResponse(tasks))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Get task by ID",
        description = "Retrieve a task by its ID for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Successfully retrieved task"),
            SwaggerApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @GetMapping("/{taskId}")
    fun getTaskById(
        @Parameter(description = "Task ID", required = true)
        @PathVariable taskId: Long,
        @Parameter(description = "User ID", required = true)
        @RequestParam userId: Long
    ): ResponseEntity<ApiResponse<TaskDTO>> {
        return try {
            val task = taskService.getTaskById(taskId, userId)
            ResponseEntity.ok(ApiResponse(task))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Update a task",
        description = "Update an existing task for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "200", description = "Task updated successfully"),
            SwaggerApiResponse(responseCode = "400", description = "Error updating task")
        ]
    )
    @PutMapping("/{taskId}")
    fun updateTask(
        @Parameter(description = "Task ID", required = true)
        @PathVariable taskId: Long,
        @Parameter(description = "User ID", required = true)
        @RequestParam userId: Long,
        @Parameter(description = "Updated task data", required = true)
        @RequestBody taskRequest: TaskDTO
    ): ResponseEntity<ApiResponse<TaskDTO>> {
        return try {
            val updatedTask = taskService.updateTask(
                taskId = taskId,
                userId = userId,
                title = taskRequest.title,
                description = taskRequest.description,
                status = taskRequest.status,
                dueDate = taskRequest.dueDate
            )
            ResponseEntity.ok(ApiResponse(updatedTask))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = "Error updating task: ${e.message}",
                    data = TaskDTO(
                        title = taskRequest.title,
                        description = taskRequest.description,
                        status = taskRequest.status,
                        dueDate = taskRequest.dueDate,
                        userId = userId,
                        categoryId = taskRequest.categoryId,
                        createdAt = null,
                        updatedAt = null
                    )
                )
            )
        }
    }

    @Operation(
        summary = "Delete a task",
        description = "Delete a task by its ID for a specific user."
    )
    @ApiResponses(
        value = [
            SwaggerApiResponse(responseCode = "204", description = "Task deleted successfully"),
            SwaggerApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @DeleteMapping("/{taskId}")
    fun deleteTask(
        @Parameter(description = "Task ID", required = true)
        @PathVariable taskId: Long,
        @Parameter(description = "User ID", required = true)
        @RequestParam userId: Long
    ): ResponseEntity<ApiResponse<Void>> {
        return try {
            taskService.deleteTask(taskId, userId)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}