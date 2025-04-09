package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.travelogue.domain.authentication.AuthService
import ru.mareanexx.travelogue.domain.authentication.dto.AuthResponse
import ru.mareanexx.travelogue.domain.authentication.dto.LoginRequest
import ru.mareanexx.travelogue.domain.authentication.dto.RegisterRequest

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.register(request))

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse?> {
        return try {
            val authResponse = authService.authenticate(request)
            ResponseEntity.ok(authResponse)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }
}