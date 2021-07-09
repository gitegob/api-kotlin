package com.gitego.todoapi.dto

import javax.validation.constraints.NotNull

data class SignupDTO(
    @field:NotNull
    var firstName: String?,
    @field:NotNull
    var lastName: String?,
    @field:NotNull
    var email: String?,
    @field:NotNull
    var username: String?,
    @field:NotNull
    var password: String?,
)
