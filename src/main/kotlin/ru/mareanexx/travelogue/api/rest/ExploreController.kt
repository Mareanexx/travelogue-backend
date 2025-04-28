package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.profile.ProfileService
import ru.mareanexx.travelogue.domain.profile.dto.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.tags.TagService
import ru.mareanexx.travelogue.domain.tags.dto.TopTag
import ru.mareanexx.travelogue.domain.trip.TripService
import ru.mareanexx.travelogue.domain.trip.dto.TrendingTrip


@RestController
@RequestMapping("/api/v1/explore")
class ExploreController(
    private val tagsService: TagService,
    private val tripService: TripService,
    private val profileService: ProfileService
) {
    @GetMapping("/tags")
    @PreAuthorize("hasRole('USER')")
    fun getTrendingTags(): ResponseEntity<WrappedResponse<List<TopTag>>> {
        return try {
            val topTagsList = tagsService.getTop()
            ResponseEntity.ok(WrappedResponse(
                message = "Successfully get trending tags",
                data = topTagsList
            ))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse(message = "Can't get trending tags"))
        }
    }

    @GetMapping("/inspiring")
    @PreAuthorize("hasRole('USER')")
    fun getInspiringProfiles(@RequestParam authorId: Int): ResponseEntity<WrappedResponse<List<InspiringProfileResponse>>> {
        return try {
            val responseList = profileService.getInspiringTravellers(authorId)
            ResponseEntity.ok(WrappedResponse(
                message = "Successfully get inspiring profiles",
                data = responseList
            ))
        } catch(e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Can't get inspiring profiles"
            ))
        }
    }

    @GetMapping("/trips")
    fun getTrendingTrips(@RequestParam authorId: Int): ResponseEntity<WrappedResponse<List<TrendingTrip>>> {
        return try {
            val trendingTrips = tripService.getFiveMostlyLikedTrips(authorId)
            ResponseEntity.ok(WrappedResponse(data = trendingTrips))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Can't get trending trips"
            ))
        }
    }
}