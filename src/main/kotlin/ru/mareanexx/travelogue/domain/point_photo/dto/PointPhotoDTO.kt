package ru.mareanexx.travelogue.domain.point_photo.dto

data class PointPhotoDTO(
    val id: Int,
    val filePath: String,
    val mapPointId: Int
)