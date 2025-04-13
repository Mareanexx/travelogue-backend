package ru.mareanexx.travelogue.domain.trip.types

import com.fasterxml.jackson.annotation.JsonProperty

enum class TripVisibilityType {
    @JsonProperty("Private") Private,
    @JsonProperty("Public") Public
}