package ru.mareanexx.travelogue.domain.tags

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "tag")
data class TagEntity(
    @Id
    val id: Int,
    val name: String
)