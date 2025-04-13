package ru.mareanexx.travelogue.domain.point_photo

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "point_photo")
data class PointPhotoEntity(
    @Id
    val id: Int? = null,
    val filePath: String,
    val mapPointId: Int // FK на map_point
)