package ru.mareanexx.travelogue.domain.map_point

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "map_point")
data class MapPointEntity(
    @Id
    val id: Int? = null,
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val likesNumber: Int = 0,
    val commentsNumber: Int = 0,
    val photosNumber: Int = 0,
    val arrivalDate: LocalDateTime,
    val tripId: Int // FK на trip
)