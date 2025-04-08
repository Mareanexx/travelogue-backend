package ru.mareanexx.travelogue.domain.tags

import org.springframework.data.relational.core.mapping.Table

@Table(name = "tags")
data class TagsEntity(
    val tripId: Int, // FK на trip
    val tagId: Int // FK на tag
)