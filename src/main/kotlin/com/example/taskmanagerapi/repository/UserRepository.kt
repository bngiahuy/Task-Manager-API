package com.example.taskmanagerapi.repository

import com.example.taskmanagerapi.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users, Long> {
    // Method to find a user by email
    fun findByEmail(email: String): Users?

    // Method to check if a user exists by email
//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.email = :email")
    fun existsByEmail(email: String): Boolean
}