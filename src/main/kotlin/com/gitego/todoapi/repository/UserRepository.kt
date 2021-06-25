package com.gitego.todoapi.repository

import com.gitego.todoapi.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByUsernameOrEmail(username: String, email: String): List<User>
}