package ru.mareanexx.travelogue.domain.report

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.report.dto.ReportWithTrip
import ru.mareanexx.travelogue.domain.report.mapper.changeStatus
import ru.mareanexx.travelogue.domain.report.mapper.mapToReport
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val tripRepository: TripRepository
) {
    /**
     * Добавление новой жалобы на контент путешествия.
     * @param tripId id путешествия, на которое отправляется жалоба
     * @throws WrongIdException если не существует trip по tripId
     */
    fun addNewReport(tripId: Int) {
        tripRepository.findById(tripId)
            .orElseThrow { WrongIdException("Ошибка при нахождении trip по tripId, невозможно добавить новый report") }
        reportRepository.save(mapToReport(tripId))
    }

    /**
     * Изменить статус report на Resolved (решена).
     * @param reportId id жалобы, у которой будет меняться статус
     */
    fun changeStatusToResolved(reportId: Int) {
        val existingReport = reportRepository.findById(reportId)
            .orElseThrow { WrongIdException("Не удалось найти report на предоставленному id") }
        val resolvedReport = existingReport.changeStatus()
        reportRepository.save(resolvedReport)
    }

    /**
     * Получение всех жалоб вместе с путешествием
     * Для модератора.
     * @return list из ReportEntity или emptyList
     */
    fun getAllForModerator(): List<ReportWithTrip> {
        return reportRepository.findAllWithTrip()
    }
}