package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.NotificationResponse

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val notificationsService: NotificationsService
) {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getAllByProfileId(@RequestParam profileId: Int): ResponseEntity<List<NotificationResponse>> {
        return try {
            val response = notificationsService.getAllByRecipientId(profileId)
            ResponseEntity.ok().body(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(emptyList())
        }
    }
}