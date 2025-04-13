package ru.mareanexx.travelogue.domain.map_point.dto

import ru.mareanexx.travelogue.domain.point_photo.dto.PointPhotoDTO
import java.time.LocalDateTime

data class UserMapPoint(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val likesNumber: Int,
    val commentsNumber: Int,
    val photosNumber: Int,
    val arrivalDate: LocalDateTime,
    val tripId: Int
)

data class UserMapPointsResponse(
    val mapPoints: List<UserMapPoint>,
    val photos: List<PointPhotoDTO>
)

data class UserMapPointResponse(
    val mapPoint: UserMapPoint,
    val photos: List<PointPhotoDTO>
)