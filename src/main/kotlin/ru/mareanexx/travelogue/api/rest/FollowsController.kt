package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.domain.follows.FollowsService
import ru.mareanexx.travelogue.domain.follows.dto.FollowUserRequest
import ru.mareanexx.travelogue.domain.follows.dto.FollowersAndFollowingsResponse

@RestController
@RequestMapping("/api/v1/follows")
class FollowsController(
    private val followsService: FollowsService
) {
    @GetMapping("/{profileId}")
    @PreAuthorize("hasRole('USER')")
    fun getAllFollowersAndFollowings(@PathVariable profileId: Int): ResponseEntity<FollowersAndFollowingsResponse?> {
        return try {
            val response = followsService.getFollowersAndFollowings(profileId)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun followUser(@RequestBody followUserRequest: FollowUserRequest): ResponseEntity<String> {
        return try {
            followsService.followNewProfile(followUserRequest)
            ResponseEntity.ok("Successfully followed new profile")
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body("Can't follow this user")
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    fun unfollowUser(@RequestBody followUserRequest: FollowUserRequest): ResponseEntity<String> {
        return try {
            followsService.unfollowProfile(followUserRequest)
            ResponseEntity.ok("Successfully unfollow user")
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body("Can't unfollow this user")
        }
    }
}