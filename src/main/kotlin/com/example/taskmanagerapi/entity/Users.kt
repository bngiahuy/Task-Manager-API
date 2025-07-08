package com.example.taskmanagerapi.entity

import com.example.taskmanagerapi.models.UsersDTO
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.USER
) : UserDetails {
    enum class UserRole : GrantedAuthority {
        USER, ADMIN;

        override fun getAuthority(): String = name // Trả về tên của enum (USER, ADMIN)
    }
    override fun getPassword(): String = passwordHash
    override fun getUsername(): String = email

    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(role)
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun toDto(): UsersDTO {
        return UsersDTO(
            id = id,
            email = email,
            role = role.name
        )
    }
}
