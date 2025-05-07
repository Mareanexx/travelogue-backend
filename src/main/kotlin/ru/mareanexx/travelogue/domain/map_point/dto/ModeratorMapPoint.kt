package ru.mareanexx.travelogue.domain.map_point.dto

import ru.mareanexx.travelogue.domain.point_photo.dto.PointPhotoDto

data class ModeratorMapPoint(
    val id: Int,
    val name: String,
    val description: String
)

data class ModeratorMapPointResponse(
    val mapPoints: List<ModeratorMapPoint>,
    val photos: List<PointPhotoDto>?
)