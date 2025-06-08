package ru.mareanexx.travelogue.support.utils.test.mapper

import ru.mareanexx.travelogue.domain.map_point.MapPointEntity
import ru.mareanexx.travelogue.domain.profile.ProfileEntity
import ru.mareanexx.travelogue.domain.trip.TripEntity
import ru.mareanexx.travelogue.support.utils.test.requests.TestMapPointRequest
import ru.mareanexx.travelogue.support.utils.test.requests.TestProfileRequest
import ru.mareanexx.travelogue.support.utils.test.requests.TestTripRequest
import java.util.*

fun TestProfileRequest.toEntity(userUuid: UUID) = ProfileEntity(
    id = null,
    username = username,
    fullName = fullName,
    bio = bio,
    avatar = avatar,
    coverPhoto = cover,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    tripsNumber = tripsNumber,
    userUUID = userUuid
)

fun TestTripRequest.toEntity(profileId: Int) = TripEntity(
    id = null,
    coverPhoto = coverPhoto,
    name = name,
    description = description,
    status = status,
    type = type,
    startDate = startDate,
    endDate = endDate,
    profileId = profileId,
    stepsNumber = stepsNumber,
    daysNumber = daysNumber
)

fun TestMapPointRequest.toEntity(tripId: Int) = MapPointEntity(
    id = null,
    longitude = longitude,
    latitude = latitude,
    name = name,
    description = description,
    likesNumber = likesNumber,
    commentsNumber = commentsNumber,
    photosNumber = photosNumber,
    arrivalDate = arrivalDate,
    tripId = tripId
)