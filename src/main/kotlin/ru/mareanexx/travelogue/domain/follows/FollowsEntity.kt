package ru.mareanexx.travelogue.domain.follows

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "follows")
data class FollowsEntity(
    val followerUuid: UUID, // FK на user
    val followingUuid: UUID, // FK на user
    val followedAt: LocalDateTime
)