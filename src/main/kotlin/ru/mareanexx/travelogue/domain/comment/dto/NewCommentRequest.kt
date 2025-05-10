package ru.mareanexx.travelogue.domain.comment.dto

import java.time.OffsetDateTime

/**
 * DTO для сохранения нового комментария к точке на карте.
 * Используется, когда пользователь отправляет комментарий к MapPoint.
*/
data class NewCommentRequest(
    val senderProfileId: Int,
    val mapPointId: Int,
    val text: String,
    val sendDate: OffsetDateTime
)