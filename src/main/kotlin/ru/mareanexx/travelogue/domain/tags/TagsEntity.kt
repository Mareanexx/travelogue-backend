package ru.mareanexx.travelogue.domain.tags

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "tags")
data class TagsEntity(
    @Id
    val id: Int? = null,
    val tripId: Int, // FK на trip
    var name: String
)