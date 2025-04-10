package ru.mareanexx.travelogue.domain.authentication

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.authentication.dto.AuthResponse
import ru.mareanexx.travelogue.domain.authentication.dto.LoginRequest
import ru.mareanexx.travelogue.domain.authentication.dto.RegisterRequest
import ru.mareanexx.travelogue.domain.authentication.jwt.JwtService
import ru.mareanexx.travelogue.domain.authentication.userDetails.CustomUserDetails
import ru.mareanexx.travelogue.domain.user.UserEntity
import ru.mareanexx.travelogue.domain.user.UserRepository
import ru.mareanexx.travelogue.domain.user.types.UserRole
import ru.mareanexx.travelogue.domain.user.types.UserStatus
import ru.mareanexx.travelogue.support.exceptions.BlockedUserException
import ru.mareanexx.travelogue.support.exceptions.EmailAlreadyExistsException
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) {
    /**
     * Регистрация пользователя.
     * @param request DTO для запроса на регистрацию (email + пароль)
     * @throws EmailAlreadyExistsException если пользователь с таким email уже существует
     */
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw EmailAlreadyExistsException("Пользователь с таким email уже существует")
        }

        val user = UserEntity(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            role = UserRole.User
        )
        val savedUser = userRepository.save(user)
        val userDetails = CustomUserDetails(user)
        val token = jwtService.generateToken(userDetails)
        return AuthResponse(userUuid = savedUser.uuid!!, token = token)
    }

    /**
     * Аутентификация пользователя.
     * @param request DTO для запроса на авторизацию (email + пароль)
     * @throws UsernameNotFoundException если пользователь не найден (или email неверен)
     * @throws BlockedUserException если пользователь пытающийся авторизоваться заблокирован
     * @throws BadCredentialsException если неверно введен пароль
     * @return userUuid и token
     */
    fun authenticate(request: LoginRequest): AuthResponse {
        val userDetails = userDetailsService.loadUserByUsername(request.email) as CustomUserDetails

        if (!passwordEncoder.matches(request.password, userDetails.password)) {
            throw BadCredentialsException("Неверно указан пароль '${request.password}'")
        }

        if (userRepository.findById(userDetails.userUuid!!).get().status == UserStatus.Blocked) {
            throw BlockedUserException("Пользователь заблокирован, запрещена аутентификация")
        }

        val token = jwtService.generateToken(userDetails)
        return AuthResponse(userUuid = userDetails.userUuid!!, token = token)
    }
}
