package com.gitego.todoapi.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GenericResponse<T>(
    val status: Int,
    val message: String,
    var timestamp: Long = Date().time,
    val data: T? = null
)