package ru.mareanexx.travelogue.domain.trip.types

import com.fasterxml.jackson.annotation.JsonProperty

enum class TripTimeStatus {
    @JsonProperty("Current") Current,
    @JsonProperty("Past") Past
}