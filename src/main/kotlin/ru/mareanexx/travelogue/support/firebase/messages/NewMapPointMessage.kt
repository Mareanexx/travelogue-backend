package ru.mareanexx.travelogue.support.firebase.messages

import ru.mareanexx.travelogue.support.firebase.type.MessageType
import ru.mareanexx.travelogue.support.firebase.type.MessageType.NewMapPoint

data class NewMapPointMessage(
    override val token: String,
    override val type: MessageType = NewMapPoint,
    override val messageBody: String,
    val tripId: Int,
    val mapPointId: Int
) : NewMessage