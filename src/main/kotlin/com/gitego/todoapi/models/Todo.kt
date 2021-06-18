package com.gitego.todoapi.models

import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String = "",
    var description: String = "",
    var completed: Boolean? = false,
    @CreatedDate
    var createdAt: Date = Date()
) {
    companion object {
        const val TODO_SEQUENCE = "TODO_SEQUENCE"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Todo

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 177241809

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}