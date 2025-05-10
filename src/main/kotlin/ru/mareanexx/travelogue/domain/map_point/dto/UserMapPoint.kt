package ru.mareanexx.travelogue.domain.map_point.dto

import ru.mareanexx.travelogue.domain.point_photo.dto.PointPhotoDto
import ru.mareanexx.travelogue.domain.trip.dto.TripResponse
import java.time.OffsetDateTime

data class UserMapPoint(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val likesNumber: Int,
    val commentsNumber: Int,
    val photosNumber: Int,
    val arrivalDate: OffsetDateTime,
    val tripId: Int,
    val isLiked: Boolean
)

data class TripWithMapPoints(
    val trip: TripResponse,
    val mapPoints: List<MapPointWithPhotos>
)

data class MapPointWithPhotos(
    val mapPoint: UserMapPoint,
    val photos: List<PointPhotoDto>
)