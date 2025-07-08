package com.example.taskmanagerapi.repository

import com.example.taskmanagerapi.entity.Category
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findByName(name: String): Category?

    fun existsByName(name: String): Boolean

    fun findByUserId(userId: Long, pageable: Pageable): List<Category>

    fun findByUserIdAndName(userId: Long, name: String): Category?

    fun findByUserIdAndId(userId: Long, id: Long): Category?
}