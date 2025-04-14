package ru.mareanexx.travelogue.support.firebase.messages

import ru.mareanexx.travelogue.support.firebase.type.MessageType

interface NewMessage {
    val token: String
    val type: MessageType
    val messageBody: String
}