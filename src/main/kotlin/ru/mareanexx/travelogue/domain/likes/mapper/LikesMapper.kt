package ru.mareanexx.travelogue.domain.likes.mapper

import ru.mareanexx.travelogue.domain.likes.LikesEntity
import ru.mareanexx.travelogue.domain.likes.dto.LikeRequest

fun LikeRequest.mapToLike() = LikesEntity(
    profileId = profileId,
    mapPointId = mapPointId
)