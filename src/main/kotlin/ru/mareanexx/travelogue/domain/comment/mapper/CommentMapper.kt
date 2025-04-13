package ru.mareanexx.travelogue.domain.comment.mapper

import ru.mareanexx.travelogue.domain.comment.CommentEntity
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentRequest
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentResponse

fun NewCommentRequest.mapToComment() = CommentEntity(
    text = text,
    sendDate = sendDate,
    senderProfileId = senderProfileId,
    mapPointId = mapPointId
)

fun CommentEntity.mapToResponse() = NewCommentResponse(
    id = id!!,
    text = this.text,
    sendDate = this.sendDate
)