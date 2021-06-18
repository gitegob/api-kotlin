package com.gitego.todoapi.dto

data class UpdateTodoDTO(
    val title: String? = null,
    val description: String? = null,
    val completed: Boolean? = null
)