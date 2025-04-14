package ru.mareanexx.travelogue.domain.notifications.dto

import ru.mareanexx.travelogue.domain.notifications.types.NotificationType

interface NewNotification {
    val senderId: Int
    val mapPointId: Int
    val commentId: Int?
    val type: NotificationType
}