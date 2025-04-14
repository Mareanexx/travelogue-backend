package ru.mareanexx.travelogue.domain.notifications.dto.comment

import ru.mareanexx.travelogue.domain.notifications.dto.NewNotification
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType.Comment

data class NewCommentNotification(
    override val mapPointId: Int,
    override val senderId: Int,
    override val commentId: Int,
    override val type: NotificationType = Comment
) : NewNotification