package ru.mareanexx.travelogue.domain.comment.dto

import java.time.LocalDateTime

data class NewCommentResponse(
    val id: Int,
    val text: String,
    val sendDate: LocalDateTime
)