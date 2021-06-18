package com.gitego.todoapi.controllers

import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.models.GenericResponse
import com.gitego.todoapi.models.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface ITodoController {
    fun findAll(completed: Boolean?,pageable: Pageable):ResponseEntity<GenericResponse<Page<Todo>>>
    fun findById(id:Long):ResponseEntity<GenericResponse<Todo>>
    fun create(createTodoDto:CreateTodoDTO):ResponseEntity<GenericResponse<Todo>>
    fun updateById(id: Long,updateTodoDTO: UpdateTodoDTO):ResponseEntity<GenericResponse<Todo>>
    fun deleteById(id:Long): ResponseEntity<GenericResponse<Unit>>
}