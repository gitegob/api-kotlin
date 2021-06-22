package com.gitego.todoapi.repository

import com.gitego.todoapi.entities.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {

    fun findByCompletedTrue(pageable: Pageable): Page<Todo>
    fun findByCompletedFalse(pageable: Pageable): Page<Todo>
    fun findByUserUsername(pageable: Pageable, username:String): Page<Todo>
    fun findByCompletedTrueAndUserUsername(pageable: Pageable,username: String): Page<Todo>
    fun findByCompletedFalseAndUserUsername(pageable: Pageable,username: String): Page<Todo>
    fun findByIdAndUserUsername(id: Long, username: String): Todo?
}