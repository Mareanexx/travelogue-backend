package ru.mareanexx.travelogue.domain.authentication.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtService {

    private val secretKey: SecretKey =
        Keys.hmacShaKeyFor("VerySecretKeyForJwtVerySecretKeyForJwt".toByteArray()) // >=32 bytes!

    fun generateToken(user: UserDetails): String =
        Jwts.builder()
            .setSubject(user.username)
            .claim("roles", user.authorities.map { it.authority })
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 86400000))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()

    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean =
        extractUsername(token) == userDetails.username
}