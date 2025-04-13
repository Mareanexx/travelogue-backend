package ru.mareanexx.travelogue.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.mareanexx.travelogue.domain.authentication.jwt.JwtAuthFilter
import ru.mareanexx.travelogue.domain.authentication.userDetails.CustomUserDetailsService

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtFilter: JwtAuthFilter,
    private val userDetailsService: CustomUserDetailsService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/v1/auth/**").permitAll()
                it.requestMatchers("/uploads/**").permitAll()
                it.anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)

    @Bean
    fun authenticationProvider(): AuthenticationProvider =
        DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder())
        }
}
