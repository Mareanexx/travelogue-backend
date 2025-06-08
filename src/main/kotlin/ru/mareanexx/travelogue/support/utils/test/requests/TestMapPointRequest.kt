package ru.mareanexx.travelogue.support.utils.test.requests

import java.time.OffsetDateTime

data class TestMapPointRequest(
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val arrivalDate: OffsetDateTime,
    val tripId: Int,
    val photosPath: List<String>,

    val likesNumber: Int = 0,
    val commentsNumber: Int = 0,
    val photosNumber: Int = 0
)