package ru.mareanexx.travelogue.api.rest

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.api.response.WrappedResponse
import ru.mareanexx.travelogue.domain.map_point.MapPointService
import ru.mareanexx.travelogue.domain.map_point.dto.*
import ru.mareanexx.travelogue.domain.notifications.NotificationsService
import ru.mareanexx.travelogue.domain.notifications.dto.mappoint.NewMapPointNotification
import ru.mareanexx.travelogue.domain.point_photo.PointPhotoService
import ru.mareanexx.travelogue.domain.point_photo.dto.PointPhotoDto

@RestController
@RequestMapping("/api/v1/map-points")
class MapPointController(
    private val mapPointService: MapPointService,
    private val pointPhotoService: PointPhotoService,
    private val notificationsService: NotificationsService
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun addNewMapPoint(
        @RequestPart("data") data: NewMapPointRequest,
        @RequestPart("photos", required = false) photos: List<MultipartFile>?
    ): ResponseEntity<WrappedResponse<MapPointWithPhotos>> {
        return try {
            val userMapPoint = mapPointService.addNewToTrip(data, photos?.size ?: 0)
            val photosList = pointPhotoService.addNewToMapPointId(userMapPoint.id, photos ?: emptyList())

            val response = MapPointWithPhotos(
                userMapPoint, photosList
            )

            notificationsService.notifyAllFollowersAboutNewMapPoint(
                NewMapPointNotification(
                    tripId = data.tripId,
                    mapPointId = userMapPoint.id
                )
            )
            ResponseEntity.ok(WrappedResponse(data = response))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(WrappedResponse(message = "Can't add new map point"))
        }
    }

    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('USER')")
    fun editMapPoint(
        @RequestPart("data") data: EditMapPointRequest,
        @RequestPart("deleted", required = false) deletedPhotos: List<DeletedPhoto>?,
        @RequestPart("photos", required = false) photos: List<MultipartFile>?
    ): ResponseEntity<MapPointWithPhotos?> {
        return try {
            val editedMapPoint = mapPointService.editMapPoint(data, changedPhotosNumber = { lastNumber ->
                lastNumber - (deletedPhotos?.size ?: 0) + (photos?.size ?: 0)
            })

            if (deletedPhotos != null) {
                pointPhotoService.deletePhotosByFilePath(deletedPhotos)
            }
            var photosResponse: List<PointPhotoDto> = emptyList()
            if (photos != null) {
                photosResponse = pointPhotoService.addNewToMapPointId(editedMapPoint.id, photos)
            }

            val response = MapPointWithPhotos(
                editedMapPoint, photosResponse
            )

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(null)
        }
    }

    @DeleteMapping("/{mapPointId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    fun deleteMapPoint(@PathVariable mapPointId: Int): ResponseEntity<String> {
        return try {
            pointPhotoService.deleteAllByMapPointId(mapPointId)
            mapPointService.deleteMapPointById(mapPointId)
            ResponseEntity.ok("Successfully deleted map point")
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body("Can't delete this mapPoint")
        }
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('USER')")
    fun getMapPointsStats(@RequestParam tripId: Int): ResponseEntity<List<UpdatedMapPointStatsResponse>> {
        return try {
            val stats = mapPointService.getAllMapPointsStats(tripId)
            ResponseEntity.ok(stats)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @GetMapping("/moder")
    @PreAuthorize("hasRole('MODERATOR')")
    fun getAllByTripIdForModerator(@RequestParam tripId: Int): ResponseEntity<ModeratorMapPointResponse?> {
        return try {
            val mapPoints = mapPointService.getAllMapPointsByTripIdForModerator(tripId)
            val photos = pointPhotoService.getAllByTripId(tripId)

            val response = ModeratorMapPointResponse(mapPoints, photos)

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }
}