package ru.mareanexx.travelogue.domain.user.types

import com.fasterxml.jackson.annotation.JsonProperty

enum class UserRole {
    @JsonProperty("Guest") Guest,
    @JsonProperty("User") User,
    @JsonProperty("Moderator") Moderator,
    @JsonProperty("Administrator") Administrator
}