package com.gitego.todoapi.services

import com.gitego.todoapi.dto.LoginDTO
import com.gitego.todoapi.dto.SignupDTO
import com.gitego.todoapi.entities.User
import com.gitego.todoapi.models.AuthData
import com.gitego.todoapi.repository.UserRepository
import com.gitego.todoapi.utils.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepo: UserRepository,
    private val jwtUtil: JwtUtil

) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val thisUser: User = userRepo.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        return org.springframework.security.core.userdetails.User(
            thisUser.username, thisUser.password,
            ArrayList()
        )
    }

    @Transactional
    fun signup(signupDTO: SignupDTO): AuthData {
        if (userRepo.findByUsernameOrEmail(
                signupDTO.username!!,
                signupDTO.email!!
            ).isNotEmpty()
        ) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Account already exists"
        )
        val newUser = User()
        newUser.firstName = signupDTO.firstName!!
        newUser.lastName = signupDTO.lastName!!
        newUser.email = signupDTO.email!!
        newUser.username = signupDTO.username!!
        newUser.password = signupDTO.password!!
        val user = this.userRepo.save(newUser)
        val jwt = jwtUtil.generateToken(user)
        return AuthData(accessToken = jwt, user = user)
    }

    fun login(loginDTO: LoginDTO): AuthData {
        val user = userRepo.findByUsername(loginDTO.username!!)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")
        if (!user.comparePwd(loginDTO.password!!)) throw ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Invalid credentials"
        )
        if (!user.active || user.accountBlocked) throw ResponseStatusException(
            HttpStatus.FORBIDDEN,
            "Account blocked or not active, please contact your admin for support"
        )
        val jwt = jwtUtil.generateToken(user)
        return AuthData(accessToken = jwt, user = user)
    }

    fun getUser(username: String): User =
        userRepo.findByUsername(username) ?: throw  ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")
}