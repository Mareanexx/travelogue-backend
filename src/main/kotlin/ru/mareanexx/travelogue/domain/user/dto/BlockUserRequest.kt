package ru.mareanexx.travelogue.domain.user.dto

import java.util.*

data class BlockUserRequest(
    val userUuid: UUID,
    val actionType: String
)
