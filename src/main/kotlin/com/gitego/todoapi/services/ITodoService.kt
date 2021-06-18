package com.gitego.todoapi.services

import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.models.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ITodoService {
    fun findById(id: Long): Todo?
    fun findAll(pageable: Pageable): Page<Todo>
    fun update(updateTodoDto: UpdateTodoDTO, id: Long): Todo
    fun deleteById(id: Long)
    fun findByCompleted(completed: Boolean,pageable: Pageable): Page<Todo>
    fun createTodo(createTodoDto: CreateTodoDTO): Todo
}