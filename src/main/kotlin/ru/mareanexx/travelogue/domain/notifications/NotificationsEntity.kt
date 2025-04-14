package ru.mareanexx.travelogue.domain.notifications

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import java.time.LocalDateTime

@Table(name = "notifications")
data class NotificationsEntity(
    @Id
    val id: Int? = null,
    val recipientId: Int, // FK на user's profile
    val senderId: Int, // FK на user's profile
    val type: NotificationType,
    val relatedTripId: Int?, // FK на trip
    val relatedPointId: Int?, // FK на map_point
    val relatedCommentId: Int?, // FK на comment
    val isRead: Boolean = false,
    val createdAt: LocalDateTime
)