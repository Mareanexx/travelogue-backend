package ru.mareanexx.travelogue.domain.trip

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import java.time.LocalDate

@Table(name = "trip")
data class TripEntity(
    @Id
    val id: Int? = null,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val stepsNumber: Int = 0,
    val daysNumber: Int = 0,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String, // relative path to photo
    val profileId: Int // FK на user's profile
)