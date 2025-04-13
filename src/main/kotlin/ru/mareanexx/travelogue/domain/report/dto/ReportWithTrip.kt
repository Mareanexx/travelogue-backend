package ru.mareanexx.travelogue.domain.report.dto

import java.time.LocalDateTime

data class ReportWithTrip(
    val reportId: Int,

    val sendDate: LocalDateTime,
    val tripName: String,
    val description: String,
    val coverPhoto: String
)