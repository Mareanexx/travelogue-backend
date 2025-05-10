package ru.mareanexx.travelogue.domain.comment.dto

import java.time.OffsetDateTime

data class NewCommentResponse(
    val id: Int,
    val text: String,
    val sendDate: OffsetDateTime
)