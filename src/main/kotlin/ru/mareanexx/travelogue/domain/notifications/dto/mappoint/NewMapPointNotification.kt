package ru.mareanexx.travelogue.domain.notifications.dto.mappoint

import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType.NewMapPoint

data class NewMapPointNotification(
    val tripId: Int,
    val mapPointId: Int,
    val type: NotificationType = NewMapPoint
)