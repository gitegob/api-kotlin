package com.gitego.todoapi.services

import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.models.Todo
import com.gitego.todoapi.repository.TodoRepository
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
) : ITodoService {
    override fun findById(id: Long): Todo? = this.findTodoById(id)

    override fun findAll(pageable: Pageable): Page<Todo> = this.todoRepo.findAll(pageable)

    override fun createTodo(createTodoDto: CreateTodoDTO): Todo {
        val newTodo = Todo()
        newTodo.title = createTodoDto.title
        newTodo.description = createTodoDto.description
        newTodo.completed = createTodoDto.completed
        return this.saveOrUpdate(newTodo)
    }

    override fun update(updateTodoDto: UpdateTodoDTO, id: Long): Todo {
        val todo = this.findTodoById(id)
        todo.title = updateTodoDto.title ?: todo.title
        todo.description = updateTodoDto.description ?: todo.description
        todo.completed = updateTodoDto.completed ?: todo.completed
        return this.saveOrUpdate(todo)
    }

    override fun findByCompleted(completed: Boolean, pageable: Pageable): Page<Todo> =
        if (completed) this.todoRepo.findByCompletedTrue(pageable) else this.todoRepo.findByCompletedFalse(pageable)

    override fun deleteById(id: Long) {
        val todo: Todo = this.findTodoById(id)
        this.todoRepo.deleteById(todo.id!!)
    }

    private fun findTodoById(id: Long): Todo = this.todoRepo.findByIdOrNull(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")

    private fun saveOrUpdate(todo: Todo): Todo = this.todoRepo.save(todo)
}