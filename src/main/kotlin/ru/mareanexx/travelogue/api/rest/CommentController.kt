package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.domain.comment.CommentEntity
import ru.mareanexx.travelogue.domain.comment.CommentService
import ru.mareanexx.travelogue.domain.comment.dto.CommentResponse
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentRequest
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentResponse
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.comment.NewCommentNotification

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService,
    private val notificationsService: NotificationsService
) {
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun addNew(@RequestBody newCommentRequest: NewCommentRequest): ResponseEntity<NewCommentResponse?> {
        return try {
            val response = commentService.addNewComment(newCommentRequest)

            notificationsService.addNewCommentNotification(
                NewCommentNotification(
                    commentId = response.id,
                    mapPointId = newCommentRequest.mapPointId,
                    senderId = newCommentRequest.senderProfileId
                )
            )

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getAllByMapPointId(@RequestParam mapPointId: Int): ResponseEntity<List<CommentResponse>> {
        return try {
            val responseComments = commentService.getAllByMapPointIdForUser(mapPointId)
            ResponseEntity.ok(responseComments)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @GetMapping("/moder")
    @PreAuthorize("hasRole('MODERATOR')")
    fun getAllByMapPointIdForModerator(@RequestParam mapPointId: Int): ResponseEntity<List<CommentEntity>> {
        return try {
            val response = commentService.getAllByMapPointForModerator(mapPointId)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    fun deleteComment(@PathVariable commentId: Int): ResponseEntity<Map<String, String>> {
        return try {
            commentService.deleteByCommentId(commentId)
            ResponseEntity.ok(mapOf("success" to "Comment was successfully deleted"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to "Can't delete comment by id"))
        }
    }
}