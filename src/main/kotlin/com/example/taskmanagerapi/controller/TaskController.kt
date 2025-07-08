package com.example.taskmanagerapi.controller

import com.example.taskmanagerapi.models.ApiResponse
import com.example.taskmanagerapi.models.TaskDTO
import com.example.taskmanagerapi.service.TaskService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/tasks")
class TaskController (
    private val taskService: TaskService,
) {
    @PostMapping("/create")
    fun createTask(@RequestBody taskRequest: TaskDTO): ResponseEntity<ApiResponse<TaskDTO>> {
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

    @GetMapping
    fun getAllTasks(
        @RequestParam(required = true) userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<List<TaskDTO>>> {
        return try {
            val tasks = taskService.getAllTasks(userId, page, size)
            ResponseEntity.ok(ApiResponse(tasks))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{taskId}")
    fun getTaskById(
        @PathVariable taskId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<ApiResponse<TaskDTO>> {
        return try {
            val task = taskService.getTaskById(taskId, userId)
            ResponseEntity.ok(ApiResponse(task))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{taskId}")
    fun updateTask(
        @PathVariable taskId: Long,
        @RequestParam userId: Long,
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

    @DeleteMapping("/{taskId}")
    fun deleteTask(
        @PathVariable taskId: Long,
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