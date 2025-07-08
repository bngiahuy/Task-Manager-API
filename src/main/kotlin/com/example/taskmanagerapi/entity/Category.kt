package com.example.taskmanagerapi.entity

import com.example.taskmanagerapi.models.CategoryDTO
import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Users? = null
) {
    fun toDto(): CategoryDTO {
        return CategoryDTO(
            id = id,
            name = name,
            userId = user?.id
        )
    }
}
