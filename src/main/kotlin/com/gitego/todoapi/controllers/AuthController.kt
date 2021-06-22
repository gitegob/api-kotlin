package com.gitego.todoapi.controllers

import com.gitego.todoapi.controllers.AuthController.Companion.BASE_AUTH_ENDPOINT
import com.gitego.todoapi.dto.LoginDTO
import com.gitego.todoapi.dto.SignupDTO
import com.gitego.todoapi.models.AuthData
import com.gitego.todoapi.models.GenericResponse
import com.gitego.todoapi.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@Validated
@RequestMapping(BASE_AUTH_ENDPOINT)
class AuthController(
    private val userService: UserService
) {

    @PostMapping("login")
    fun login(
        @RequestBody @Valid loginDTO: LoginDTO,
        response: HttpServletResponse
    ): ResponseEntity<GenericResponse<AuthData>> {
        val result = userService.login(loginDTO)
        val cookie = Cookie("jwt", result.accessToken)
        // Sending the token in a cookie
        response.addCookie(cookie)
        return ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "Login successful",
                data = result
            )
        )
    }

    @PostMapping("signup")
    fun signup(
        @RequestBody @Valid signupDTO: SignupDTO,
        response: HttpServletResponse
    ): ResponseEntity<GenericResponse<AuthData>> {
        val result = userService.signup(signupDTO)
        // Sending the token in a cookie
        val cookie = Cookie("jwt", result.accessToken)
        response.addCookie(cookie)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            GenericResponse(
                status = HttpStatus.CREATED.value(),
                message = "Signup successful",
                data = result
            )
        )
    }

    @GetMapping("user")
    fun getUser(@RequestParam("jwt") jwt: String?): ResponseEntity<GenericResponse<Any>> {
        val user = userService.getUser(jwt) as Map<*, *>
        return ResponseEntity.ok(
            GenericResponse(
                status = HttpStatus.OK.value(),
                message = "User data retrieved",
                data = user["id"]
            )
        )
    }
//    @GetMapping("user")
//     fun getUser(@CookieValue("jwt") jwt: String?): ResponseEntity<GenericResponse<Any>> {
//        return ResponseEntity.ok(
//            GenericResponse(
//                status = HttpStatus.OK.value(),
//                message = "User data retrieved",
//                data = userService.getUser(jwt)
//            )
//        )
//    }

    companion object {
        const val BASE_AUTH_ENDPOINT = "/api/v1/auth"
    }
}