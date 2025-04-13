package ru.mareanexx.travelogue.domain.report

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.report.dto.ReportWithTrip

@Repository
interface ReportRepository : CrudRepository<ReportEntity, Int> {

    @Query("""
        SELECT r.id AS report_id, r.send_date, t.id, t.name AS trip_name,
            t.description, t.cover_photo
        FROM "report" r
        JOIN "trip" t ON r.trip_id = t.id
        WHERE r.status = 'New'
    """)
    fun findAllWithTrip(): List<ReportWithTrip>
}