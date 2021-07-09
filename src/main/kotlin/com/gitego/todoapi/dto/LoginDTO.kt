package com.gitego.todoapi.dto

import javax.validation.constraints.NotNull

data class LoginDTO(
    @field:NotNull
    var username: String?,
    @field:NotNull
    var password: String?,
)
