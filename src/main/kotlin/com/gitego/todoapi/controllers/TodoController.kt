package com.gitego.todoapi.controllers

import com.gitego.todoapi.controllers.TodoController.Companion.BASE_TODO_ENDPOINT
import com.gitego.todoapi.dto.CreateTodoDTO
import com.gitego.todoapi.dto.UpdateTodoDTO
import com.gitego.todoapi.entities.Todo
import com.gitego.todoapi.models.GenericResponse
import com.gitego.todoapi.models.PaginatedData
import com.gitego.todoapi.services.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@RestController
@Validated
@RequestMapping(BASE_TODO_ENDPOINT)
class TodoController(
    private val todoService: TodoService,
) {

    @Operation(summary = "Get all todos", security = [SecurityRequirement(name = SECURITY_SCHEME_NAME)])
    @GetMapping
    fun findAll(
        completed: Boolean?,
        pageable: Pageable,
        principal: Principal
    ): ResponseEntity<GenericResponse<PaginatedData<Todo>>> =
        if (completed == null) ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todos retrieved",
                data = toPaginatedData(this.todoService.findAll(pageable, principal.name))
            )
        ) else ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todos retrieved",
                data = toPaginatedData(
                    this.todoService.findByCompleted(
                        completed,
                        pageable,
                        principal.name
                    )
                )
            )
        )

    @Operation(summary = "Get one todo", security = [SecurityRequirement(name = SECURITY_SCHEME_NAME)])
    @GetMapping("/{id}")
    fun findById(
        @Valid @PathVariable @Min(1,message = "id must not be less than 1") @Max(2,message = "id must not be less than 2") id: Long,
        principal: Principal
    ): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo retrieved",
                data = this.todoService.findById(id, principal.name)
            )
        )

    @Operation(summary = "Create a todo", security = [SecurityRequirement(name = SECURITY_SCHEME_NAME)])
    @PostMapping
    fun create(
        @Valid @RequestBody createTodoDto: CreateTodoDTO,
        principal: Principal
    ): ResponseEntity<GenericResponse<Todo>> {
        return ResponseEntity
            .status(HttpStatus.CREATED.value())
            .body(
                GenericResponse(
                    status = HttpStatus.OK.value(),
                    message = "Todo created",
                    data = this.todoService.createTodo(
                        createTodoDto,
                        principal.name
                    )
                )
            )
    }

    @Operation(summary = "Update a todo", security = [SecurityRequirement(name = SECURITY_SCHEME_NAME)])
    @PutMapping("/{id}")
    fun updateById(
        @PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) id: Long,
        @RequestBody @Valid updateTodoDTO: UpdateTodoDTO,
        principal: Principal
    ): ResponseEntity<GenericResponse<Todo>> =
        ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo updated",
                data = this.todoService.update(
                    updateTodoDTO,
                    id,
                    principal.name
                )
            )
        )

    @Operation(summary = "Delete a todo", security = [SecurityRequirement(name = SECURITY_SCHEME_NAME)])
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable(
            "id"
        ) @Min(1) @Max(Long.MAX_VALUE) id: Long,
        principal: Principal
    ): ResponseEntity<GenericResponse<Unit>> {
        this.todoService.deleteById(id, principal.name)
        return ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Todo deleted"
            )
        )
    }

    private fun toPaginatedData(page: Page<Todo>): PaginatedData<Todo> = PaginatedData(
        content = page.content,
        sorted = page.sort.isSorted,
        pageSize = page.size,
        totalPages = page.totalPages,
        totalElements = page.totalElements
    )


    companion object {
        const val BASE_TODO_ENDPOINT: String = "/api/v1/todos"
        const val SECURITY_SCHEME_NAME: String = "bearerAuth"
    }
}