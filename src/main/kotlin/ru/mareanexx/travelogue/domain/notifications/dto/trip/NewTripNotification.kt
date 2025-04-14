package ru.mareanexx.travelogue.domain.notifications.dto.trip

import ru.mareanexx.travelogue.domain.notifications.types.NotificationType
import ru.mareanexx.travelogue.domain.notifications.types.NotificationType.NewTrip

data class NewTripNotification(
    val creatorId: Int,
    val tripId: Int,
    val type: NotificationType = NewTrip
)