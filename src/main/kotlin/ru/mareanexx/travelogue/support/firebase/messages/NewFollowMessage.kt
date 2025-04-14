package ru.mareanexx.travelogue.support.firebase.messages

import ru.mareanexx.travelogue.support.firebase.type.MessageType
import ru.mareanexx.travelogue.support.firebase.type.MessageType.NewFollow

data class NewFollowMessage(
    override val token: String,
    override val type: MessageType = NewFollow,
    override val messageBody: String,
    val followerId: Int
) : NewMessage
