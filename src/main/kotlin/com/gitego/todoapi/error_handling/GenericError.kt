package com.gitego.todoapi.error_handling

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GenericError(
        var status: Int = 0,
        var error: String? = null,
        var validationErrors: MutableMap<String, String>? = null,
        var path: String? = null,
        var timestamp: Long = Date().time,
        )