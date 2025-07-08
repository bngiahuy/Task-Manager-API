package com.example.taskmanagerapi.service

import com.example.taskmanagerapi.models.TaskDTO

interface TaskService {
    //Chứa logic tạo, lấy, cập nhật, xóa tác vụ.
    //
    //Quan trọng: Đảm bảo chỉ người dùng sở hữu tác vụ mới có thể quản lý tác vụ đó.
    //
    //Triển khai phân trang, lọc (theo status, category, title) và sắp xếp (theo dueDate, createdAt).

    fun createTask(title: String, description: String, status: String, dueDate: String?, userId: Long, categoryId: Long?): TaskDTO
    fun getTaskById(taskId: Long, userId: Long): TaskDTO
    fun updateTask(taskId: Long, userId: Long, title: String?, description
: String?, status: String?, dueDate: String?): TaskDTO
    fun deleteTask(taskId: Long, userId: Long)
    fun getAllTasks(userId: Long, page: Int, size: Int, status
: String? = null, categoryId: Long? = null, title: String? = null, sortBy: String? = null): List<TaskDTO>
}