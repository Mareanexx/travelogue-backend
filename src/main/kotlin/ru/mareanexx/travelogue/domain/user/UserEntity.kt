package ru.mareanexx.travelogue.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.travelogue.domain.user.types.UserRole
import ru.mareanexx.travelogue.domain.user.types.UserStatus
import java.util.*

@Table(name = "user")
data class UserEntity(
    @Id
    val uuid: UUID? = null,
    val role: UserRole,
    val email: String,
    val passwordHash: String,
    val status: UserStatus = UserStatus.Active
)