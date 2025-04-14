package ru.mareanexx.travelogue.domain.notifications.dto.follows

import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType.Follow

data class NewFollowsNotification(
    val followerId: Int,
    val followingId: Int,
    val type: NotificationType = Follow
)