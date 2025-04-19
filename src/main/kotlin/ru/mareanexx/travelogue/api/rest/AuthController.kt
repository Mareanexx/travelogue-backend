package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.authentication.AuthService
import ru.mareanexx.travelogue.domain.authentication.dto.AuthResponse
import ru.mareanexx.travelogue.domain.authentication.dto.LoginRequest
import ru.mareanexx.travelogue.domain.authentication.dto.RegisterRequest
import ru.mareanexx.travelogue.support.exceptions.EmailAlreadyExistsException

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<WrappedResponse<AuthResponse>> {
        return try {
            val registerResponse = authService.register(request)
            ResponseEntity.ok(WrappedResponse(
                message = "Successfully register user",
                data = registerResponse
            ))
        } catch (e: EmailAlreadyExistsException) {
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "User with such email already exists",
            ))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<WrappedResponse<AuthResponse>> {
        return try {
            val authResponse = authService.authenticate(request)
            ResponseEntity.ok(WrappedResponse(
                message = "Successful authentication",
                data = authResponse
            ))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }
}