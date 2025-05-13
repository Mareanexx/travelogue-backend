package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.follows.FollowsService
import ru.mareanexx.travelogue.domain.follows.dto.FollowUserRequest
import ru.mareanexx.travelogue.domain.follows.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.follows.NewFollowsNotification

@RestController
@RequestMapping("/api/v1/follows")
class FollowsController(
    private val followsService: FollowsService,
    private val notificationsService: NotificationsService
) {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getAllFollowersAndFollowings(
        @RequestParam authorId: Int,
        @RequestParam othersId: Int
    ): ResponseEntity<WrappedResponse<FollowersAndFollowingsResponse>> {
        return try {
            val response = followsService.getFollowersAndFollowings(authorId, othersId)
            ResponseEntity.ok(WrappedResponse(
                data = response
            ))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Can't get user's follows"
            ))
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun followUser(@RequestBody followUserRequest: FollowUserRequest): ResponseEntity<WrappedResponse<String>> {
        return try {
            followsService.followNewProfile(followUserRequest)

            notificationsService.addNewFollowerNotification(
                NewFollowsNotification(
                    followerId = followUserRequest.followerId,
                    followingId = followUserRequest.followingId
                )
            )

            ResponseEntity.ok(WrappedResponse(
                message = "Successfully followed new user's profile"
            ))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Can't follow this user"
            ))
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    fun unfollowUser(
        @RequestParam followerId: Int,
        @RequestParam followingId: Int
    ): ResponseEntity<WrappedResponse<String>> {
        return try {
            followsService.unfollowProfile(followerId, followingId)
            ResponseEntity.ok(WrappedResponse(message = "Successfully unfollow user"))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse("Can't unfollow this user"))
        }
    }
}