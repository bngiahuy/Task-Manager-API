package com.example.taskmanagerapi.service

import com.example.taskmanagerapi.models.UsersDTO

interface UserService {
    //  Chứa logic đăng ký, đăng nhập, tải người dùng theo username.
    fun registerUser(email: String, password: String): UsersDTO
    fun loginUser(email: String, password: String): UsersDTO
    fun getUserByEmail(email: String): UsersDTO
    fun getUserById(id: Long): UsersDTO
}