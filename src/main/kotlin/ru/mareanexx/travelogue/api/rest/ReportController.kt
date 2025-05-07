package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.report.ReportService
import ru.mareanexx.travelogue.domain.report.dto.ReportWithTrip
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@RestController
@RequestMapping("/api/v1/reports")
class ReportController(
    private val reportService: ReportService
) {
    @GetMapping
    @PreAuthorize("hasRole('MODERATOR')")
    fun getAll(): ResponseEntity<List<ReportWithTrip>> {
        return ResponseEntity.ok(reportService.getAllForModerator())
    }

    @PatchMapping("/{reportId}")
    @PreAuthorize("hasRole('MODERATOR')")
    fun resolveReport(@PathVariable reportId: Int): ResponseEntity<String> {
        return try {
            reportService.changeStatusToResolved(reportId)
            ResponseEntity.ok("Successfully changed status to Resolved")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Can't change report status to resolved")
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun addNewReport(@RequestParam tripId: Int): ResponseEntity<WrappedResponse<Nothing>> {
        return try {
            reportService.addNewReport(tripId)
            ResponseEntity.ok(WrappedResponse(
                message = "Thank you, your report has been received!"
            ))
        } catch(e: WrongIdException) {
            ResponseEntity.badRequest().body(WrappedResponse(message = "Trip with such id wasn't found"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse(message = "Can't add new report"))
        }
    }
}