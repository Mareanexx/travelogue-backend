package ru.mareanexx.travelogue.api.rest

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.map_point.MapPointService
import ru.mareanexx.travelogue.domain.map_point.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.domain.map_point.dto.TripWithMapPoints
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.trip.NewTripNotification
import ru.mareanexx.travelogue.domain.point_photo.PointPhotoService
import ru.mareanexx.travelogue.domain.tags.TagService
import ru.mareanexx.travelogue.domain.trip.TripService
import ru.mareanexx.travelogue.domain.trip.dto.*

@RestController
@RequestMapping("/api/v1/trips")
class TripController(
    private val tripService: TripService,
    private val tagService: TagService,
    private val mapPointService: MapPointService,
    private val pointPhotoService: PointPhotoService,
    private val notificationsService: NotificationsService
) {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getTripWithMapPoints(
        @RequestParam authorId: Int,
        @RequestParam tripId: Int
    ): ResponseEntity<WrappedResponse<TripWithMapPoints>> {
        return try {
            val trip = tripService.getTrip(tripId)
            trip.tagList = tagService.getAllByTripId(tripId)
    
            val mapPoints = mapPointService.getAllByTripId(authorId, tripId)
            val pointsPhotos = pointPhotoService.getAllByTripId(tripId)
            val mapPointsResponse: List<MapPointWithPhotos> = mapPoints.map { mapPoint ->
                MapPointWithPhotos(mapPoint, pointsPhotos.filter { it.mapPointId == mapPoint.id })
            }
            ResponseEntity.ok(WrappedResponse(data = TripWithMapPoints(trip, mapPointsResponse)))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse(message = "Can't get trip with map points"))
        }
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun uploadNewTrip(
        @RequestPart("data") data: NewTripRequest,
        @RequestPart("cover") coverPhoto: MultipartFile
    ): ResponseEntity<WrappedResponse<TripResponse>> {
        return try {
            val newTrip = tripService.createNewTrip(data, coverPhoto)
            data.tagList?.let {
                val tags = tagService.addNew(it, newTrip.id)
                newTrip.tagList = tags
            }

            notificationsService.notifyAllFollowersAboutNewTrip(
                NewTripNotification(
                    creatorId = data.profileId,
                    tripId = newTrip.id
                )
            )
            ResponseEntity.ok(WrappedResponse(data = newTrip))
        } catch(e: Exception) {
            ResponseEntity.badRequest().body(WrappedResponse(message = "Can't add a new trip"))
        }
    }

    @DeleteMapping("/{tripId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    fun deleteTrip(@PathVariable tripId: Int): ResponseEntity<WrappedResponse<String>> {
        return try {
            tripService.deleteTrip(tripId)
            ResponseEntity.ok(WrappedResponse("Successfully deleted trip"))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse("Can't delete this trip"))
        }
    }

    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun editTrip(
        @RequestPart("data") data: EditTripRequest,
        @RequestPart("cover") coverPhoto: MultipartFile?
    ): ResponseEntity<WrappedResponse<TripResponse>> {
        return try {
            val editedTrip = tripService.editTrip(data, coverPhoto)
            data.tagList?.let {
                val tags = tagService.editTags(it, editedTrip.id)
                editedTrip.tagList = tags
            }
            ResponseEntity.ok(WrappedResponse(
                message = "Successfully update trip",
                data = editedTrip
            ))
        } catch(e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(
                message = "Can't update this trip : ${e.message}"
            ))
        }
    }

    @GetMapping("/tagged")
    @PreAuthorize("hasRole('USER')")
    fun getAllByTag(@RequestBody tripByTag: TripByTagRequest): ResponseEntity<List<TripByTag>> {
        return try {
            val trips = tripService.getAllByTag(tripByTag)
            ResponseEntity.ok(trips)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @GetMapping("/activity")
    @PreAuthorize("hasRole('USER')")
    fun getAllFollowingsCurrent(@RequestParam authorId: Int): ResponseEntity<List<ActiveFollowingTrip>> {
        return try {
            val currentTrips = tripService.getAllFollowingsCurrentTrips(authorId)
            ResponseEntity.ok(currentTrips)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(emptyList())
        }
    }
}