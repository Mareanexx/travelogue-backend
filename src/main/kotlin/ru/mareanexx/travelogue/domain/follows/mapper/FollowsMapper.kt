package ru.mareanexx.travelogue.domain.follows.mapper

import ru.mareanexx.travelogue.domain.follows.FollowsEntity
import ru.mareanexx.travelogue.domain.follows.dto.FollowUserRequest
import java.time.LocalDateTime

fun FollowUserRequest.mapToFollows() = FollowsEntity(
    followerId = followerId,
    followingId = followingId,
    followedAt = LocalDateTime.now()
)