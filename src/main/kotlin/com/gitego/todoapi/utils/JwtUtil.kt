package com.gitego.todoapi.utils

import com.gitego.todoapi.entities.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    fun generateToken(user: User): String = Jwts.builder()
        .setIssuedAt(Date())
        .setIssuer(user.id.toString())
        .setSubject(user.username)
        .claim("user", user)
        .setExpiration(Date(System.currentTimeMillis() + 3600 * 24 * 1000))
        .signWith(SignatureAlgorithm.HS256, jwtSecret)
        .compact()

    fun verifyToken(token: String?): Claims {
        if (token == null) throw  ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized request")
        try {
            return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized request")
        }
    }

    fun isTokenExpired(token: String): Boolean {
        return verifyToken(token).expiration.before(Date())
    }

    fun getUsername(token: String?): String? {
        return verifyToken(token).subject
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username: String = getUsername(token)!!
        return username == userDetails.username && !isTokenExpired(token!!)
    }

}