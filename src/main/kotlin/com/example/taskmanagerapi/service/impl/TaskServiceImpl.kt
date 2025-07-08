package com.example.taskmanagerapi.service.impl

import com.example.taskmanagerapi.repository.CategoryRepository
import com.example.taskmanagerapi.repository.TaskRepository
import com.example.taskmanagerapi.repository.UserRepository
import com.example.taskmanagerapi.service.TaskService
import com.example.taskmanagerapi.entity.Task
import com.example.taskmanagerapi.models.TaskDTO
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskServiceImpl (
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) : TaskService {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(TaskServiceImpl::class.java)
    }
    override fun createTask(
        title: String,
        description: String,
        status: String,
        dueDate: String?,
        userId: Long,
        categoryId: Long?
    ): TaskDTO {
        try {
            val user = userRepository.findById(userId).orElseThrow {
                IllegalArgumentException("User with ID $userId not found")
            }
            val category = categoryId?.let {
                categoryRepository.findById(categoryId).orElseThrow {
                    IllegalArgumentException("Category with ID $categoryId not found")
                }
            }
            val statusEnum = Task.TaskStatus.valueOf(status.uppercase())
            val task = Task(
                title = title,
                description = description,
                status = statusEnum,
                dueDate = dueDate?.let { LocalDate.parse(it) },
                user = user,
                category = category
            )
            val result = taskRepository.save(task).toDto()
            logger.info("Task created successfully with ID: ${task.id}")
            return result
        } catch (e: Exception) {
            logger.error("Error creating task: ${e.message}", e)
            throw e
        }
    }

    override fun getTaskById(taskId: Long, userId: Long): TaskDTO {
        try {
            val task = taskRepository.findById(taskId).orElseThrow {
                IllegalArgumentException("Task with ID $taskId not found")
            }
            if (task.user.id != userId) {
                throw IllegalAccessException("User does not have permission to access this task")
            }
            logger.info("Task with ID $taskId retrieved successfully")
            return task.toDto()
        } catch (e: Exception) {
            logger.error("Error retrieving task: ${e.message}", e)
            throw e // Rethrow the exception to be handled by the controller
        }
    }

    override fun updateTask(
        taskId: Long,
        userId: Long,
        title: String?,
        description: String?,
        status: String?,
        dueDate: String?
    ): TaskDTO {
        try {
            val task = taskRepository.findById(taskId).orElseThrow {
                IllegalArgumentException("Task with ID $taskId not found")
            }
            if (task.user.id != userId) {
                throw IllegalAccessException("User does not have permission to update this task")
            }
            title?.let { task.title = it }
            description?.let { task.description = it }
            status?.let { task.status = Task.TaskStatus.valueOf(it.uppercase()) }
            dueDate?.let { task.dueDate = LocalDate.parse(it) }

            task.updatedAt = LocalDate.now() // Update the updatedAt field
            val updatedTask = taskRepository.save(task)
            logger.info("Task with ID $taskId updated successfully")
            return updatedTask.toDto()
        } catch (e: Exception) {
            logger.error("Error updating task: ${e.message}", e)
            throw e // Rethrow the exception to be handled by the controller
        }
    }

    override fun deleteTask(taskId: Long, userId: Long) {
        try {
            val task = taskRepository.findById(taskId).orElseThrow {
                IllegalArgumentException("Task with ID $taskId not found")
            }
            if (task.user.id != userId) {
                throw IllegalAccessException("User does not have permission to delete this task")
            }
            taskRepository.delete(task)
            logger.info("Task with ID $taskId deleted successfully")
        } catch (e: Exception) {
            logger.error("Error deleting task: ${e.message}", e)
            throw e // Rethrow the exception to be handled by the controller
        }
    }

    override fun getAllTasks(
        userId: Long,
        page: Int,
        size: Int,
        status: String?,
        categoryId: Long?,
        title: String?,
        sortBy: String?
    ): List<TaskDTO> {
        try {
            val user = userRepository.findById(userId).orElseThrow {
                IllegalArgumentException("User with ID $userId not found")
            }
            val pageable = org.springframework.data.domain.PageRequest.of(page, size)
            val tasks = taskRepository.findAllByUserId(
                userId, pageable
            )
            logger.info("Retrieved ${tasks.size} tasks for user with ID $userId")
            return tasks.map { it.toDto() }
        } catch (e: Exception) {
            logger.error("Error retrieving tasks: ${e.message}", e)
            throw e // Rethrow the exception to be handled by the controller
        }
    }
}