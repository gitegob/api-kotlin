package com.gitego.todoapi.entities

import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import java.util.*
import javax.persistence.*
import javax.persistence.GenerationType.*

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    @GeneratedValue(strategy = AUTO)
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var completed: Boolean? = false,
    @CreatedDate
    var createdAt: Date = Date(),
    @ManyToOne
    var user: User? = null
)