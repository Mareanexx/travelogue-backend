package ru.mareanexx.travelogue.api.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.profile.ProfileEntity
import ru.mareanexx.travelogue.domain.profile.ProfileService
import ru.mareanexx.travelogue.domain.profile.dto.NewProfileRequest
import ru.mareanexx.travelogue.domain.profile.dto.UpdateProfileRequest
import ru.mareanexx.travelogue.domain.profile.dto.fcm.UpdateTokenRequest
import ru.mareanexx.travelogue.domain.profile.dto.stats.UpdatedProfileStatsResponse
import ru.mareanexx.travelogue.domain.profile.dto.withTrips.AuthorProfileResponse
import ru.mareanexx.travelogue.domain.profile.dto.withTrips.UserProfileResponse
import ru.mareanexx.travelogue.domain.trip.TripService
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import java.util.*

@RestController("customProfileController")
@RequestMapping("/api/v1/profile")
class ProfileController(
    private val profileService: ProfileService,
    private val tripService: TripService
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun uploadNewProfile(
        @RequestPart("data") mainData: NewProfileRequest,
        @RequestPart("avatar") avatar: MultipartFile?,
    ): ResponseEntity<WrappedResponse<ProfileEntity>> {
        return try {
            val newProfile = profileService.createNewProfile(avatar, mainData)
            ResponseEntity(WrappedResponse(data = newProfile), HttpStatus.CREATED)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(message = "Error in creating new profile"))
        }
    }
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getAuthorsProfile(@RequestParam authorUuid: UUID): ResponseEntity<WrappedResponse<AuthorProfileResponse>> {
        return try {
            val authorProfile = profileService.getAuthorsProfile(authorUuid)
            val trips = tripService.getAuthorsTrips(authorProfile.id)
            val profile = AuthorProfileResponse(profile = authorProfile, trips = trips)

            ResponseEntity.ok(WrappedResponse(
                message = "Profile was found",
                data = profile
            ))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse(message = "Can't find profile by profile id"))
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


    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun editProfile(
        @RequestPart("data") data: UpdateProfileRequest,
        @RequestPart("avatar") avatar: MultipartFile?,
        @RequestPart("cover") cover: MultipartFile?
    ): ResponseEntity<WrappedResponse<ProfileEntity>> {
        return try {
            val editedProfile = profileService.updateProfile(avatar, cover, data)
            ResponseEntity.ok(
                WrappedResponse(
                    message = "Profile information successfully changed",
                    data = editedProfile
                )
            )
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Error in editing user's profile"
            ))
        }
    }

    @PatchMapping("/token")
    @PreAuthorize("hasRole('USER')")
    fun updateFcmToken(@RequestBody newToken: UpdateTokenRequest): ResponseEntity<Map<String, String>> {
        return try {
            profileService.updateToken(newToken)
            ResponseEntity.ok(mapOf("success" to "Successfully updated profile token"))
        } catch (e: WrongIdException) {
            ResponseEntity.badRequest().body(mapOf("error" to "Can't find profile by id"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to "Can't update user token"))
        }
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('USER')")
    fun getUpdatedStats(@RequestParam authorId: Int): ResponseEntity<WrappedResponse<UpdatedProfileStatsResponse>> {
        return try {
            val updatedStats = profileService.getUpdatedStats(authorId)
            ResponseEntity.ok(WrappedResponse(
                message = "Successfully get updated stats",
                data = updatedStats
            ))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Can't get updated stats: ${e.message}"
            ))
        }
    }
}