package com.gitego.todoapi.services

import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.entities.Todo
import com.gitego.todoapi.repository.TodoRepository
import com.gitego.todoapi.repository.UserRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.transaction.Transactional

@Service
@Transactional
class TodoService(
    private val todoRepo: TodoRepository,
    private val userRepo: UserRepository
) {
    fun findById(id: Long, username: String): Todo? =
        todoRepo.findByIdAndUserUsername(id, username) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Todo not found"
        )

    @Transactional
    fun findAll(pageable: Pageable?, username: String): Page<Todo> {
        return this.todoRepo.findByUserUsername(pageable, username)
    }

    @Transactional
    fun createTodo(createTodoDto: CreateTodoDTO, username: String): Todo {
        val user = userRepo.findByUsername(username)
        val newTodo = Todo()
        newTodo.title = createTodoDto.title!!
        newTodo.description = createTodoDto.description!!
        newTodo.user = user
        return this.saveOrUpdate(newTodo)
    }

    fun update(updateTodoDto: UpdateTodoDTO, id: Long, username: String): Todo {
        val todo = todoRepo.findByIdAndUserUsername(id, username)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")
        todo.title = updateTodoDto.title ?: todo.title
        todo.description = updateTodoDto.description ?: todo.description
        todo.completed = updateTodoDto.completed ?: todo.completed
        return this.saveOrUpdate(todo)
    }

    fun findByCompleted(completed: Boolean, pageable: Pageable, username: String): Page<Todo> =
        if (completed) this.todoRepo.findByCompletedTrueAndUserUsername(
            pageable,
            username
        ) else this.todoRepo.findByCompletedFalseAndUserUsername(pageable, username)

    fun deleteById(id: Long, username: String) {
        val todo: Todo = todoRepo.findByIdAndUserUsername(id, username)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")
        this.todoRepo.deleteById(todo.id)
    }

    private fun saveOrUpdate(todo: Todo): Todo = this.todoRepo.save(todo)
}