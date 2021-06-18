package com.gitego.todoapi.controllers

import com.gitego.todoapi.controllers.TodoController.Companion.BASE_TODO_URL
import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.models.GenericResponse
import com.gitego.todoapi.models.Todo
import com.gitego.todoapi.services.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@RestController
@Validated
@RequestMapping(BASE_TODO_URL)
class TodoController(
    private val todoService: TodoService
) : ITodoController {

    @GetMapping
    override fun findAll(completed: Boolean?, pageable: Pageable): ResponseEntity<GenericResponse<Page<Todo>>> =
        if (completed == null) ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todos retrieved",
                data = this.todoService.findAll(pageable)
            )
        ) else ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todos retrieved",
                data = this.todoService.findByCompleted(completed, pageable)
            )
        )

    @GetMapping("/{id}")
    override fun findById(@PathVariable("id") @Valid @Min(1) @Max(Long.MAX_VALUE) id: Long): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo retrieved",
                data = this.todoService.findById(id)
            )
        )

    @PostMapping
    override fun create(@Valid @RequestBody createTodoDto: CreateTodoDTO): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity
            .status(HttpStatus.CREATED.value())
            .body(
                GenericResponse(
                    status = HttpStatus.OK.value(),
                    message = "Todo created",
                    data = this.todoService.createTodo(createTodoDto)
                )
            )

    @PutMapping("/{id}")
    override fun updateById(
        @PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) id: Long,
        @Valid @RequestBody updateTodoDTO: UpdateTodoDTO
    ): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo updated",
                data = this.todoService.update(updateTodoDTO, id)
            )
        )

    @DeleteMapping("/{id}")
    override fun deleteById(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) id: Long): ResponseEntity<GenericResponse<Unit>> {
        this.todoService.deleteById(id)
        return ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo deleted"
            )
        )
    }

    companion object {
        const val BASE_TODO_URL: String = "/api/v1/todos"
    }
}