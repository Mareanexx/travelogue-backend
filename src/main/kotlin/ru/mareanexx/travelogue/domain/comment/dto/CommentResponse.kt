package ru.mareanexx.travelogue.domain.comment.dto

import java.time.OffsetDateTime

/**
 * DTO для получения комментария под mapPoint.
 * Используется, когда пользователь переходит на MapPoint и нужно
 * получить все комментарии.
*/
data class CommentResponse(
    val id: Int,
    val text: String,
    val sendDate: OffsetDateTime,
    val senderProfileId: Int,
    val username: String,
    val avatar: String?
)