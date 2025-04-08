package ru.mareanexx.travelogue.domain.notifications

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "notifications")
data class NotificationsEntity(
    @Id
    val id: Int,
    val recipientUuid: UUID, // FK на user
    val senderUuid: UUID?, // FK на user
    val type: NotificationType,
    val relatedTripId: Int?, // FK на trip
    val relatedPointId: Int?, // FK на map_point
    val relatedCommentId: Int?, // FK на comment
    val idRead: Boolean,
    val createdAt: LocalDateTime
)