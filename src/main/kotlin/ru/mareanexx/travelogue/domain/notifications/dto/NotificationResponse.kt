package ru.mareanexx.travelogue.domain.notifications.dto

import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import java.time.OffsetDateTime

data class NotificationResponse(
    val id: Int,
    val senderId: Int,

    val avatar: String?,
    val username: String,

    val type: NotificationType,
    val relatedTripId: Int?,
    val relatedPointId: Int?,
    val relatedCommentId: Int?,
    val isRead: Boolean,
    val createdAt: OffsetDateTime
)