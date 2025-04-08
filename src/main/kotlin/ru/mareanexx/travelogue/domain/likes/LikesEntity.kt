package ru.mareanexx.travelogue.domain.likes

import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table(name = "likes")
data class LikesEntity(
    val userUuid: UUID, // FK на user
    val mapPointId: Int // FK на map_point
)
