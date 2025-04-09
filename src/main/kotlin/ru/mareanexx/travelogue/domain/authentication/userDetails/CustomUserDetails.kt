package ru.mareanexx.travelogue.domain.authentication.userDetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.mareanexx.travelogue.domain.user.UserEntity
import ru.mareanexx.travelogue.domain.user.types.UserStatus

class CustomUserDetails(
    private val user: UserEntity
) : UserDetails {
    val userUuid
        get() = user.uuid

    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_${user.role.name.uppercase()}"))

    override fun getPassword(): String = user.passwordHash
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = user.status == UserStatus.Active
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}