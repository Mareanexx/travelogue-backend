package ru.mareanexx.travelogue.domain.notifications.types

import com.fasterxml.jackson.annotation.JsonProperty

enum class NotificationType {
    @JsonProperty("Like") Like,
    @JsonProperty("Comment") Comment,
    @JsonProperty("Follow") Follow,
    @JsonProperty("NewTrip") NewTrip,
    @JsonProperty("NewMapPoint") NewMapPoint
}