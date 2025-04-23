package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.NotificationResponse

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val notificationsService: NotificationsService
) {

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getAllByProfileId(@RequestParam profileId: Int): ResponseEntity<WrappedResponse<List<NotificationResponse>>> {
        val response = notificationsService.getAllByRecipientId(profileId)
        return ResponseEntity.ok(
            WrappedResponse(
                message = "Successfully found user's notifications",
                data = response
            )
        )
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    fun clearAllNotifications(@RequestParam profileId: Int): ResponseEntity<WrappedResponse<Nothing>> {
        notificationsService.deleteAllByProfileId(profileId)
        return ResponseEntity.ok(
            WrappedResponse(
                message = "Successfully deleted all notifications"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<WrappedResponse<Nothing>> {
        return ResponseEntity.badRequest().body(
            WrappedResponse(
                message = "${ex.message}",
                data = null
            )
        )
    }
}