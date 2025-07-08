package com.example.taskmanagerapi.service

import com.example.taskmanagerapi.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val usersRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return usersRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found: $username")
    }
}