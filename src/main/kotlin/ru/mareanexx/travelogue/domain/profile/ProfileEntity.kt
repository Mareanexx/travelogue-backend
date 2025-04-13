package ru.mareanexx.travelogue.domain.profile

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "profile")
data class ProfileEntity(
    @Id
    val id: Int? = null,
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String?, // relative path to photo
    val coverPhoto: String?, // relative path to photo
    val followersNumber: Int = 0,
    val followingNumber: Int = 0,
    val tripsNumber: Int = 0,
    val userUUID: UUID // FK на user
)