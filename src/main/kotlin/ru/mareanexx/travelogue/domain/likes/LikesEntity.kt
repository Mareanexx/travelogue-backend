package ru.mareanexx.travelogue.domain.likes

import org.springframework.data.relational.core.mapping.Table

@Table(name = "likes")
data class LikesEntity(
    val profileId: Int, // FK на user's profile
    val mapPointId: Int // FK на map_point
)
