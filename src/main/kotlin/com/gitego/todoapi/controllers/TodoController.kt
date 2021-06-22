package com.gitego.todoapi.controllers

import com.gitego.todoapi.controllers.TodoController.Companion.BASE_TODO_ENDPOINT
import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.models.GenericResponse
import com.gitego.todoapi.entities.Todo
import com.gitego.todoapi.services.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@RestController
@Validated
@RequestMapping(BASE_TODO_ENDPOINT)
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun findAll(
        completed: Boolean?,
        pageable: Pageable,
        @CurrentSecurityContext context: SecurityContext
    ): ResponseEntity<GenericResponse<Page<Todo>>> =
        if (completed == null) ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todos retrieved",
                data = this.todoService.findAll(pageable, context.authentication.name)
            )
        ) else ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todos retrieved",
                data = this.todoService.findByCompleted(completed, pageable, context.authentication.name)
            )
        )

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") @Valid @Min(1) @Max(Long.MAX_VALUE) id: Long,
        @CurrentSecurityContext context: SecurityContext
    ): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo retrieved",
                data = this.todoService.findById(id, context.authentication.name)
            )
        )

    @PostMapping
    fun create(
        @Valid @RequestBody createTodoDto: CreateTodoDTO,
        @CurrentSecurityContext context: SecurityContext
    ): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity
            .status(HttpStatus.CREATED.value())
            .body(
                GenericResponse(
                    status = HttpStatus.OK.value(),
                    message = "Todo created",
                    data = this.todoService.createTodo(createTodoDto, context.authentication.name)
                )
            )

    @PutMapping("/{id}")
    fun updateById(
        @PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) id: Long,
        @Valid @RequestBody updateTodoDTO: UpdateTodoDTO,
        @CurrentSecurityContext context: SecurityContext
    ): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo updated",
                data = this.todoService.update(updateTodoDTO, id, context.authentication.name)
            )
        )

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable(
            "id"
        ) @Min(1) @Max(Long.MAX_VALUE) id: Long,
        @CurrentSecurityContext context: SecurityContext
    ): ResponseEntity<GenericResponse<Unit>> {
        this.todoService.deleteById(id, context.authentication.name)
        return ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo deleted"
            )
        )
    }

    companion object {
        const val BASE_TODO_ENDPOINT: String = "/api/v1/todos"
    }
}