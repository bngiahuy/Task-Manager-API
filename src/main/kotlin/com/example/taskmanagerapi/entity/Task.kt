package com.example.taskmanagerapi.entity
import com.example.taskmanagerapi.models.TaskDTO
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String,

    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.PENDING,

    @Column(nullable = true)
    var dueDate: LocalDate?,

    @Column(nullable = false)
    val createdAt: LocalDate = LocalDate.now(),

    @Column(nullable = false)
    var updatedAt: LocalDate = LocalDate.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Users,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    val category: Category? = null
) {
    enum class TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED;
    }

    fun toDto(): TaskDTO {
        return TaskDTO(
            id = id,
            title = title,
            description = description,
            status = status.name,
            dueDate = dueDate.toString(),
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString(),
            userId = user.id,
            categoryId = category?.id
        )
    }
}
