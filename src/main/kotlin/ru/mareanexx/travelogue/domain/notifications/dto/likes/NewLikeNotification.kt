package ru.mareanexx.travelogue.domain.notifications.dto.likes

import ru.mareanexx.travelogue.domain.notifications.dto.NewNotification
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType.Like

data class NewLikeNotification(
    override val mapPointId: Int,
    override val senderId: Int,
    override val commentId: Int? = null,
    override val type: NotificationType = Like
) : NewNotification