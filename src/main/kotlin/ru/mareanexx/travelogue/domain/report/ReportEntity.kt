package ru.mareanexx.travelogue.domain.report

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "report")
data class ReportEntity(
    @Id
    val id: Int,
    val sendDate: LocalDateTime,
    val tripId: Int // FK на trip
)