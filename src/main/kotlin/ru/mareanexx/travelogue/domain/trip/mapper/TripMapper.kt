package ru.mareanexx.travelogue.domain.trip.mapper

import ru.mareanexx.travelogue.domain.trip.TripEntity
import ru.mareanexx.travelogue.domain.trip.dto.EditTripRequest
import ru.mareanexx.travelogue.domain.trip.dto.NewTripRequest
import ru.mareanexx.travelogue.domain.trip.dto.TripResponse

fun NewTripRequest.mapToTrip(
    coverPath: String
) = TripEntity(
    name = name,
    description = description,
    status = status,
    startDate = startDate,
    endDate = endDate,
    profileId = profileId,
    type = type,
    coverPhoto = coverPath
)

fun TripEntity.copyChangedProperties(
    editTripRequest: EditTripRequest,
    coverPath: String
): TripEntity {
    return this.copy(
        name = editTripRequest.name ?: this.name,
        description = editTripRequest.description ?: this.description,
        startDate = editTripRequest.startDate ?: this.startDate,
        endDate = editTripRequest.endDate,
        type = editTripRequest.type ?: this.type,
        status = editTripRequest.status ?: this.status,
        coverPhoto = coverPath
    )
}

fun TripEntity.mapToResponse() = TripResponse(
    id = id!!,
    name = name,
    description = description,
    startDate = startDate,
    endDate = endDate,
    stepsNumber = stepsNumber,
    daysNumber = daysNumber,
    type = type,
    status = status,
    coverPhoto = coverPhoto,
    profileId = profileId
)