package ru.mareanexx.travelogue.support.firebase.messages

import ru.mareanexx.travelogue.support.firebase.type.MessageType
import ru.mareanexx.travelogue.support.firebase.type.MessageType.NewLike

data class NewLikeMessage(
    override val token: String,
    override val type: MessageType = NewLike,
    override val messageBody: String,
    val mapPointId: Int,
    val tripId: Int
) : NewMessage