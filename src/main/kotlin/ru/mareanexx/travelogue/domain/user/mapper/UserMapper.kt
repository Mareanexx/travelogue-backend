package ru.mareanexx.travelogue.domain.user.mapper

import ru.mareanexx.travelogue.domain.user.UserEntity

fun UserEntity.changeProps(newEmail: String) = UserEntity(
    uuid = uuid,
    role = role,
    email = newEmail,
    passwordHash = passwordHash
)