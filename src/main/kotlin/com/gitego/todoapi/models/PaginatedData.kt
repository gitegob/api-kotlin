package com.gitego.todoapi.models

data class PaginatedData<T>(
    val content: List<T>,
    val sorted: Boolean,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Long
)