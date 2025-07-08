package com.example.taskmanagerapi.repository

import com.example.taskmanagerapi.entity.Task
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {
    /**
     * Finds all tasks associated with a specific user ID.
     *
     * @param userId the ID of the user whose tasks are to be retrieved.
     * @return a list of tasks associated with the specified user ID.
     */
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.createdAt DESC")
    fun findAllByUserId(userId: Long, pageable: Pageable): List<Task>
}