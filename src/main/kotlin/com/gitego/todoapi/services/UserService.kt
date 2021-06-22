package com.gitego.todoapi.services

import com.gitego.todoapi.dto.LoginDTO
import com.gitego.todoapi.dto.SignupDTO
import com.gitego.todoapi.models.AuthData
import com.gitego.todoapi.entities.User
import com.gitego.todoapi.repository.UserRepository
import com.gitego.todoapi.utils.JwtUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.java.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
@Log
class UserService(
    private val userRepo: UserRepository,
    private val jwtUtil: JwtUtil

) {

    fun signup(signupDTO: SignupDTO): AuthData {
        val newUser = User()
        newUser.firstName = signupDTO.firstName
        newUser.lastName = signupDTO.lastName
        newUser.email = signupDTO.email
        newUser.username = signupDTO.username
        newUser.password = signupDTO.password
        val user = this.userRepo.save(newUser)
        val jwt = jwtUtil.generateToken(user)
        return AuthData(accessToken = jwt, user = user)
    }

    fun login(loginDTO: LoginDTO): AuthData {
        val user = userRepo.findByUsername(loginDTO.username)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")
        if (!user.comparePwd(loginDTO.password)) throw ResponseStatusException(
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

    fun getUser(token: String?): Any? {
        val result = jwtUtil.verifyToken(token)
        return result["user"]
    }
}