package ru.mareanexx.travelogue.domain.profile

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table(name = "profile")
data class ProfileEntity(
    @Id
    val id: Int,
    val fullName: String,
    val bio: String,
    val avatar: String?, // relative path to photo
    val coverPhoto: String?, // relative path to photo
    val tripsNumber: Int,
    val userUuid: UUID // FK на user
)