package ru.mareanexx.travelogue.domain.report

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.travelogue.domain.report.type.ReportStatus
import java.time.LocalDateTime

@Table(name = "report")
data class ReportEntity(
    @Id
    val id: Int? = null,
    val sendDate: LocalDateTime,
    val status: ReportStatus = ReportStatus.New,
    val tripId: Int // FK на trip
)