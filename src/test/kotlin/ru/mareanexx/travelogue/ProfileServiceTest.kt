package ru.mareanexx.travelogue

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.profile.ProfileService
import ru.mareanexx.travelogue.domain.profile.dto.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.profile.dto.ProfileDTO
import ru.mareanexx.travelogue.domain.profile.dto.fcm.UpdateTokenRequest
import ru.mareanexx.travelogue.domain.user.UserRepository
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import ru.mareanexx.travelogue.support.utils.PhotoService
import java.util.*

@ExtendWith(MockKExtension::class)
class ProfileServiceTest {
    @MockK
    lateinit var profileRepository: ProfileRepository
    @MockK
    lateinit var userRepository: UserRepository
    @MockK
    lateinit var photoService: PhotoService
    private lateinit var profileService: ProfileService

    private val avatarsPath = "/mock/avatars"
    private val coversPath = "/mock/covers"

    @BeforeEach
    fun setUp() {
        profileService = ProfileService(
            avatarsPath = avatarsPath, coversPath = coversPath,
            profileRepository = profileRepository, userRepository = userRepository, photoService = photoService
        )
    }

    @Test
    fun `getAuthorsProfile should return profile if user exists`() {
        // given
        val uuid = UUID.randomUUID()
        every { userRepository.findById(uuid) } returns Optional.of(mockk())
        val expectedDto = mockk<ProfileDTO>()
        every { profileRepository.findByUserUUID(uuid) } returns expectedDto

        // when
        val result = profileService.getAuthorsProfile(uuid)

        // then
        assertEquals(expectedDto, result)
        verify(exactly = 1) { userRepository.findById(uuid) }
        verify(exactly = 1) { profileRepository.findByUserUUID(uuid) }
    }

    @Test
    fun `getAuthorsProfile should throw if user not found`() {
        val uuid = UUID.randomUUID()
        every { userRepository.findById(uuid) } returns Optional.empty()

        val exception = assertThrows<WrongIdException> {
            profileService.getAuthorsProfile(uuid)
        }

        assertEquals("Не удалось найти пользователя по uuid", exception.message)
    }

    @Test
    fun `updateToken should update token if profile exists`() {
        val request = UpdateTokenRequest(profileId = 1, fcmToken = "abc123")

        every { profileRepository.findById(1) } returns Optional.of(mockk())
        every { profileRepository.updateTokenByProfileId(1, "abc123") } just Runs

        profileService.updateToken(request)

        verify { profileRepository.findById(1) }
        verify { profileRepository.updateTokenByProfileId(1, "abc123") }
    }

    @Test
    fun `updateToken should throw if profile not found`() {
        val request = UpdateTokenRequest(profileId = 99, fcmToken = "abc123")
        every { profileRepository.findById(99) } returns Optional.empty()

        val ex = assertThrows<WrongIdException> {
            profileService.updateToken(request)
        }

        assertEquals("Не удалось найти профиль по id", ex.message)
    }

    @Test
    fun `getInspiringTravellers should return list of profiles`() {
        val authorId = 123
        val expectedList = listOf(mockk<InspiringProfileResponse>())
        every { profileRepository.findOrderedByTripsNumber(authorId) } returns expectedList

        val result = profileService.getInspiringTravellers(authorId)

        assertEquals(expectedList, result)
        verify { profileRepository.findOrderedByTripsNumber(authorId) }
    }
}
