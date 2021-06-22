package com.gitego.todoapi.services


import com.gitego.todoapi.entities.User
import com.gitego.todoapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    private val userRepo: UserRepository? = null
    override fun loadUserByUsername(username: String): UserDetails {
        val thisUser: User = userRepo?.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        return org.springframework.security.core.userdetails.User(
            thisUser.username, thisUser.password,
            ArrayList()
        )
    }

//    fun save(user: UserDTO): User {
//        val newUser = User()
//        newUser.setUsername(user.getUsername())
//        newUser.setPassword(passwordEncoder!!.encode(user.getPassword()))
//        return userRepo!!.save(newUser)
//    }
}