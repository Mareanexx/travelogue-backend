package ru.mareanexx.travelogue

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.domain.profile.ProfileEntity
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.trip.TripEntity
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.TripService
import ru.mareanexx.travelogue.domain.trip.dto.*
import ru.mareanexx.travelogue.domain.trip.mapper.mapToResponse
import ru.mareanexx.travelogue.domain.trip.mapper.mapToTrip
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import ru.mareanexx.travelogue.support.utils.PhotoService
import java.time.LocalDate
import java.util.*


@ExtendWith(MockKExtension::class)
class TripServiceTest {

    @MockK
    lateinit var tripRepository: TripRepository
    @MockK lateinit var profileRepository: ProfileRepository
    @MockK lateinit var photoService: PhotoService

    @InjectMockKs
    lateinit var tripService: TripService

    private val coversPath = "uploads/trips"

    @BeforeEach
    fun setUp() {
        every { photoService.saveFile(any(), any(), any()) } returns "trip/covers/cover.jpg"
        every { photoService.deleteFileIfExists(any()) } just Runs
    }

    @Test
    fun `getAuthorsTrips should return trips by authorId`() {
        val authorId = 1
        val trips = listOf(mockk<AuthorTrip>())
        every { tripRepository.findAllByAuthorProfileId(authorId) } returns trips

        val result = tripService.getAuthorsTrips(authorId)

        assertEquals(trips, result)
    }

    @Test
    fun `createNewTrip should save new trip and return response`() {
        val newTrip = NewTripRequest(
            profileId = 1,
            name = "Trip",
            description = "Desc",
            tagList = null,
            status = TripTimeStatus.Past,
            startDate = LocalDate.now(),
            type = TripVisibilityType.Public
        )
        val tripEntity = mockk<TripEntity>()
        val savedTrip = mockk<TripEntity>()
        val response = mockk<TripResponse>()
        val cover = mockk<MultipartFile>()
        val profile = mockk<ProfileEntity>()

        mockkStatic("ru.mareanexx.travelogue.domain.trip.mapper.TripMapperKt")

        every { profileRepository.findById(1) } returns Optional.of(profile)

        every { photoService.saveFile(cover, any(), any()) } returns "trip/covers/cover.jpg"
        every { newTrip.mapToTrip("trip/covers/cover.jpg") } returns tripEntity
        every { tripRepository.save(tripEntity) } returns savedTrip
        every { savedTrip.mapToResponse() } returns response

        val result = tripService.createNewTrip(newTrip, cover)

        assertEquals(response, result)

        unmockkStatic("ru.mareanexx.travelogue.domain.trip.mapper.TripMapperKt")
    }


    @Test
    fun `deleteTrip should delete trip and its photo`() {
        val tripId = 1
        val trip = TripEntity(id = tripId, name = "T", description = "D", startDate = LocalDate.now(),
            type = TripVisibilityType.Public, status = TripTimeStatus.Current,
            coverPhoto = "trip/covers/img.jpg", profileId = 1)

        every { tripRepository.findById(tripId) } returns Optional.of(trip)
        every { tripRepository.delete(trip) } just Runs

        tripService.deleteTrip(tripId)

        verify { photoService.deleteFileIfExists(trip.coverPhoto) }
        verify { tripRepository.delete(trip) }
    }

    @Test
    fun `editTrip should update trip with new cover`() {
        val request = EditTripRequest(id = 1, name = "Updated", description = "Updated", tagList = null, endDate = LocalDate.now())
        val oldTrip = TripEntity(1, "Old", "Old", LocalDate.now(), null, 0, 0,
            TripVisibilityType.Public, TripTimeStatus.Past, "old/cover.jpg", 1)
        val updatedTrip = oldTrip.copy(name = "Updated", description = "Updated", endDate = request.endDate, coverPhoto = "new/cover.jpg")
        val cover = mockk<MultipartFile>()
        val response = mockk<TripResponse>()

        mockkStatic("ru.mareanexx.travelogue.domain.trip.mapper.TripMapperKt")

        every { tripRepository.findById(1) } returns Optional.of(oldTrip)
        every { photoService.saveFile(cover, coversPath, TripService.COVER_PATH_MIDDLE) } returns "new/cover.jpg"
        every { tripRepository.save(any()) } returns updatedTrip
        every { any<TripEntity>().mapToResponse() } returns response

        val result = tripService.editTrip(request, cover)

        assertEquals(response, result)

        unmockkStatic("ru.mareanexx.travelogue.domain.trip.mapper.TripMapperKt")
    }

    @Test
    fun `getAllByTag should return trips by tag`() {
        val request = TripByTagRequest(tagName = "Nature", finderId = 5)
        val trips = listOf(mockk<TripByTag>())
        every { tripRepository.findAllByTag("Nature", 5) } returns trips

        val result = tripService.getAllByTag(request)

        assertEquals(trips, result)
    }

    @Test
    fun `getAllPublicOthersTrips should return public trips`() {
        val profileId = 2
        val trips = listOf(mockk<UserTrip>())
        every { tripRepository.findByProfileIdFilterByStatus(profileId) } returns trips

        val result = tripService.getAllPublicOthersTrips(profileId)

        assertEquals(trips, result)
    }

    @Test
    fun `getAllFollowingsCurrentTrips should return current trips of followings`() {
        val profileId = 2
        val trips = listOf(mockk<ActiveFollowingTrip>())
        every { tripRepository.findAllByStatusAndFollowerId(profileId) } returns trips

        val result = tripService.getAllFollowingsCurrentTrips(profileId)

        assertEquals(trips, result)
    }

    @Test
    fun `getFiveMostlyLikedTrips should return trending trips`() {
        val profileId = 3
        val trips = listOf(mockk<TrendingTrip>())
        every { tripRepository.findFiveByLikesNumber(profileId) } returns trips

        val result = tripService.getFiveMostlyLikedTrips(profileId)

        assertEquals(trips, result)
    }
}