package com.gitego.todoapi.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import javax.persistence.*
import javax.persistence.GenerationType.AUTO

@Entity
@Table(name = "users")
class User {

    // USER ENTITY ATTRIBUTES
    @Id
    @GeneratedValue(strategy = AUTO)
    var id: Long = 0

    @Column
    var firstName: String = ""

    @Column
    var lastName: String = ""

    @Column(unique = true)
    var email: String = ""

    @Column(unique = true)
    var username: String = ""

    @JsonIgnore
    var password: String = ""
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    @Column
    var active: Boolean = true

    @Column
    var accountBlocked: Boolean = false

    @CreatedDate
    var createdAt: Date = Date()

    @OneToMany
    var todos: List<Todo>? = null


    // USER METHODS
    fun comparePwd(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}
