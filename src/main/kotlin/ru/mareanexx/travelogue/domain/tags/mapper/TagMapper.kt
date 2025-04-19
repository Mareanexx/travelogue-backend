package ru.mareanexx.travelogue.domain.tags.mapper

import ru.mareanexx.travelogue.domain.tags.TagsEntity
import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.tags.dto.TagResponse

fun NewTag.mapToTags(tripId: Int) = TagsEntity(
    name = name,
    tripId = tripId
)

fun TagsEntity.toDto() = TagResponse(
    id = id!!,
    name = name
)