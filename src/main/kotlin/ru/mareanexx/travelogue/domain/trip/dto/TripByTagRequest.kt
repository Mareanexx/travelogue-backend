package ru.mareanexx.travelogue.domain.trip.dto

data class TripByTagRequest(
    val finderId: Int, // FK to sender request user's profile
    val tagName: String
)