package ru.mareanexx.travelogue.domain.comment.dto

import java.time.LocalDateTime

/**
 * DTO для получения комментария под mapPoint.
 * Используется, когда пользователь переходит на MapPoint и нужно
 * получить все комментарии.
*/
data class CommentResponse(
    val id: Int,
    val text: String,
    val sendDate: LocalDateTime,
    val senderProfileId: Int,
    val username: String,
    val avatar: String?
)