package ru.mareanexx.travelogue.support.firebase.messages

import ru.mareanexx.travelogue.support.firebase.type.MessageType
import ru.mareanexx.travelogue.support.firebase.type.MessageType.NewTrip

data class NewTripMessage(
    override val token: String,
    override val type: MessageType = NewTrip,
    override val messageBody: String,
    val tripId: Int
) : NewMessage