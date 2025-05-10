package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.likes.LikesService
import ru.mareanexx.travelogue.domain.likes.dto.LikeRequest
import ru.mareanexx.travelogue.domain.likes.type.LikeStatusCode.*
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.likes.NewLikeNotification

@RestController
@RequestMapping("/api/v1/likes")
class LikesController(
    private val likesService: LikesService,
    private val notificationsService: NotificationsService
) {
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun addNew(@RequestBody likeRequest: LikeRequest): ResponseEntity<WrappedResponse<String>> {
        return try {
            val statusCode = likesService.addNew(likeRequest)
            val response = WrappedResponse(
                data = when (statusCode) {
                    SUCCESS -> "You successfully likes map_point"
                    ERROR -> "Something went wrong, can't add new like"
                    UNKNOWN -> "Unknown error on server"
                }
            )

            notificationsService.addNewLikeNotification(
                NewLikeNotification(
                    mapPointId = likeRequest.mapPointId,
                    senderId = likeRequest.profileId,
                )
            )

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse("Can't add new like"))
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    fun deleteExisting(@RequestBody likeRequest: LikeRequest): ResponseEntity<WrappedResponse<String>> {
        return try {
            val statusCode = likesService.deleteExisted(likeRequest)
            val response = WrappedResponse(
                data = when (statusCode) {
                    SUCCESS -> "You successfully unliked map_point"
                    ERROR -> "Something went wrong, can't delete like"
                    UNKNOWN -> "Unknown error on server"
                }
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse("Can't delete like on map_point"))
        }
    }
}