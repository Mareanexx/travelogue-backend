package ru.mareanexx.travelogue.domain.user.types

import com.fasterxml.jackson.annotation.JsonProperty

enum class UserStatus {
    @JsonProperty("Active") Active,
    @JsonProperty("Blocked") Blocked
}