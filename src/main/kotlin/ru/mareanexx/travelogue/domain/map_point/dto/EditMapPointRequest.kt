package ru.mareanexx.travelogue.domain.map_point.dto

import java.time.LocalDateTime

data class EditMapPointRequest(
    val id: Int,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val name: String? = null,
    val description: String? = null,
    val arrivalDate: LocalDateTime? = null,
)