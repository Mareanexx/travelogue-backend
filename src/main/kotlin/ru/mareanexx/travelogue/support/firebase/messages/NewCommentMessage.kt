package ru.mareanexx.travelogue.support.firebase.messages

import ru.mareanexx.travelogue.support.firebase.type.MessageType
import ru.mareanexx.travelogue.support.firebase.type.MessageType.NewComment

data class NewCommentMessage(
    override val token: String,
    override val type: MessageType = NewComment,
    override val messageBody: String,
    val mapPointId: Int,
    val tripId: Int,
    val commentId: Int
) : NewMessage