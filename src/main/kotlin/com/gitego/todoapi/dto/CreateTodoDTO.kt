package com.gitego.todoapi.dto

import javax.validation.constraints.NotNull

data class CreateTodoDTO(
    @field:NotNull(message = "title cannot be null")
    var title: String,

    @field:NotNull(message = "description cannot be null")
    var description: String,

    var completed: Boolean = false
)