package ru.mareanexx.travelogue.domain.tags.mapper

import ru.mareanexx.travelogue.domain.tags.TagsEntity
import ru.mareanexx.travelogue.domain.tags.dto.NewTag

fun NewTag.mapToTags(tripId: Int) = TagsEntity(
    name = name,
    tripId = tripId
)