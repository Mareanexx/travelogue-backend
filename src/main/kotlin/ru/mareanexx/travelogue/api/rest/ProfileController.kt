package ru.mareanexx.travelogue.api.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.domain.profile.ProfileEntity
import ru.mareanexx.travelogue.domain.profile.ProfileService
import ru.mareanexx.travelogue.domain.profile.dto.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.profile.dto.NewProfileRequest
import ru.mareanexx.travelogue.domain.profile.dto.UpdateProfileRequest
import ru.mareanexx.travelogue.domain.profile.dto.stats.UpdatedProfileStatsResponse
import ru.mareanexx.travelogue.domain.profile.dto.withTrips.AuthorProfileResponse
import ru.mareanexx.travelogue.domain.profile.dto.withTrips.UserProfileResponse
import ru.mareanexx.travelogue.domain.trip.TripService
import java.util.*

@RestController("customProfileController")
@RequestMapping("/api/v1/profile")
class ProfileController(
    private val profileService: ProfileService,
    private val tripService: TripService
) {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getAuthorsProfile(@RequestParam authorUuid: UUID): ResponseEntity<AuthorProfileResponse?> {
        return try {
            val authorProfile = profileService.getAuthorsProfile(authorUuid)
            val trips = tripService.getAuthorsTrips(authorProfile.id)

            val response = AuthorProfileResponse(
                profile = authorProfile,
                trips = trips
            )

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/other/{profileId}")
    @PreAuthorize("hasRole('USER')")
    fun getOtherUsersProfile(@PathVariable profileId: Int): ResponseEntity<UserProfileResponse?> {
        return try {
            val userProfile = profileService.getOtherUserProfile(profileId)
            val userTrips = tripService.getAllPublicOthersTrips(profileId)

            val response = UserProfileResponse(
                profile = userProfile,
                trips = userTrips
            )

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun uploadNewProfile(
        @RequestPart("data") mainData: NewProfileRequest,
        @RequestPart("avatar") avatar: MultipartFile?,
        @RequestPart("coverPhoto") coverPhoto: MultipartFile?
    ): ResponseEntity<ProfileEntity?> {
        return try {
            val newProfile = profileService.createNewProfile(avatar, coverPhoto, mainData)
            ResponseEntity(newProfile, HttpStatus.CREATED)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }

    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun editProfile(
        @RequestPart("data") mainData: UpdateProfileRequest,
        @RequestPart("avatar") avatar: MultipartFile?,
        @RequestPart("coverPhoto") coverPhoto: MultipartFile?
    ): ResponseEntity<ProfileEntity?> {
        return try {
            val editedProfile = profileService.updateProfile(avatar, coverPhoto, mainData)
            ResponseEntity.ok(editedProfile)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/inspiring")
    @PreAuthorize("hasRole('USER')")
    fun getInspiring(): ResponseEntity<List<InspiringProfileResponse>> {
        return try {
            val responseList = profileService.getInspiringTravellers()
            ResponseEntity.ok(responseList)
        } catch(e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @GetMapping("/stats/{authorId}")
    @PreAuthorize("hasRole('USER')")
    fun getUpdatedStats(@PathVariable authorId: Int): ResponseEntity<UpdatedProfileStatsResponse?> {
        return try {
            val updatedStats = profileService.getUpdatedStats(authorId)
            ResponseEntity.ok(updatedStats)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }
}