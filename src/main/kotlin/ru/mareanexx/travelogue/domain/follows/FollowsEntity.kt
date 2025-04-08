package ru.mareanexx.travelogue.domain.follows

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "follows")
data class FollowsEntity(
    val followerId: Int, // FK на user's profile
    val followingId: Int, // FK на user's profile
    val followedAt: LocalDateTime
)