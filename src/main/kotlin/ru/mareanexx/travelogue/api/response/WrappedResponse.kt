package ru.mareanexx.travelogue.api.response

data class WrappedResponse<T>(
    val message: String? = null,
    val data: T? = null
)