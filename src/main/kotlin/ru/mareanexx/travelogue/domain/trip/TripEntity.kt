package ru.mareanexx.travelogue.domain.trip

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "trip")
data class TripEntity(
    @Id
    val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val stepsNumber: Int,
    val daysNumber: Int,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String, // relative path to photo
    val userUuid: UUID // FK на user
)