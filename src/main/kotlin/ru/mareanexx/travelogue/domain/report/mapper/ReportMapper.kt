package ru.mareanexx.travelogue.domain.report.mapper

import ru.mareanexx.travelogue.domain.report.ReportEntity
import ru.mareanexx.travelogue.domain.report.type.ReportStatus
import java.time.LocalDateTime

fun mapToReport(tripId: Int): ReportEntity {
    return ReportEntity(
        tripId = tripId,
        sendDate = LocalDateTime.now()
    )
}

fun ReportEntity.changeStatus() = ReportEntity(
    id = id,
    status = ReportStatus.Resolved,
    sendDate = sendDate,
    tripId = tripId
)