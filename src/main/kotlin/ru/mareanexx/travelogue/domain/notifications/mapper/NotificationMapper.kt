package ru.mareanexx.travelogue.domain.notifications.mapper

import ru.mareanexx.travelogue.domain.notifications.NotificationsEntity
import ru.mareanexx.travelogue.domain.notifications.dto.NewNotification
import ru.mareanexx.travelogue.domain.notifications.dto.follows.NewFollowsNotification
import java.time.LocalDateTime

fun NewNotification.mapToEntity(
    recipientId: Int,
    tripId: Int
) = NotificationsEntity(
    recipientId = recipientId,
    senderId = senderId,
    type = type,
    relatedTripId = tripId,
    relatedPointId = mapPointId,
    relatedCommentId = commentId,
    createdAt = LocalDateTime.now()
)

fun NewFollowsNotification.mapToEntity() = NotificationsEntity(
    recipientId = followingId,
    senderId = followerId,
    type = type,
    relatedCommentId = null,
    relatedPointId = null,
    relatedTripId = null,
    createdAt = LocalDateTime.now()
)