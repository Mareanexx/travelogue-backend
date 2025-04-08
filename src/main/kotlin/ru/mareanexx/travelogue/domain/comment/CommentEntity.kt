package ru.mareanexx.travelogue.domain.comment

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "comment")
data class CommentEntity(
    @Id
    val id: Int,
    val text: String,
    val sendDate: LocalDateTime,
    val userUuid: UUID, // FK на user
    val mapPointId: Int // FK на map_point
)
