package com.gitego.todoapi.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

data class CreateTodoDTO(
    @field:NotNull
    @Parameter(description = "Title", example = "New Todo", required = true)
    var title: String?,

    @field:NotNull
    @Parameter(description = "Description", example = "Finish up all pending assignments", required = true)
    var description: String?,
)