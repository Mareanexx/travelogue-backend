package ru.mareanexx.travelogue.domain.map_point.mapper

import ru.mareanexx.travelogue.domain.map_point.MapPointEntity
import ru.mareanexx.travelogue.domain.map_point.dto.EditMapPointRequest
import ru.mareanexx.travelogue.domain.map_point.dto.NewMapPointRequest
import ru.mareanexx.travelogue.domain.map_point.dto.UserMapPoint

fun NewMapPointRequest.mapToMapPoint(
    photosNumber: Int
) = MapPointEntity(
    longitude = longitude,
    latitude = latitude,
    name = name,
    description = description,
    photosNumber = photosNumber,
    arrivalDate = arrivalDate,
    tripId = tripId
)

fun MapPointEntity.mapToResponse() = UserMapPoint(
    id!!, longitude, latitude, name, description, likesNumber, commentsNumber, photosNumber, arrivalDate, tripId, false
)

fun MapPointEntity.copyChangedProperties(
    editedMapPoint: EditMapPointRequest,
    newPhotosNumber: Int
): MapPointEntity {
    return this.copy(
        longitude = editedMapPoint.longitude ?: this.longitude,
        latitude = editedMapPoint.latitude ?: this.latitude,
        name = editedMapPoint.name ?: this.name,
        photosNumber = newPhotosNumber,
        description = editedMapPoint.description ?: this.description,
        arrivalDate = editedMapPoint.arrivalDate ?: this.arrivalDate
    )
}