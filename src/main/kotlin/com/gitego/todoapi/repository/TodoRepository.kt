package com.gitego.todoapi.repository

import com.gitego.todoapi.models.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {

    fun findByCompletedTrue(pageable: Pageable): Page<Todo>
    fun findByCompletedFalse(pageable: Pageable): Page<Todo>
}