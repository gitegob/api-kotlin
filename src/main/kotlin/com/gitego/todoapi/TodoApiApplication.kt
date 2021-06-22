package com.gitego.todoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@RestController
class TodoApiApplication {
    @RequestMapping("/")
    fun root(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello")
    }
}

fun main(args: Array<String>) {
    runApplication<TodoApiApplication>(*args)
}
