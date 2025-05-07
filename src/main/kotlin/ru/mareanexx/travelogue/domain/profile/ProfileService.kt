package ru.mareanexx.travelogue.domain.profile

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.domain.profile.dto.*
import ru.mareanexx.travelogue.domain.profile.dto.fcm.UpdateTokenRequest
import ru.mareanexx.travelogue.domain.profile.dto.stats.UpdatedProfileStatsResponse
import ru.mareanexx.travelogue.domain.profile.mapper.copyChangedProperties
import ru.mareanexx.travelogue.domain.profile.mapper.mapToProfile
import ru.mareanexx.travelogue.domain.user.UserRepository
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import ru.mareanexx.travelogue.support.utils.PhotoService
import java.util.*

@Service
class ProfileService(
    @Value("\${app.uploads.avatars-path}") private val avatarsPath: String,
    @Value("\${app.uploads.cover-path}") private val coversPath: String,
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
    private val photoService: PhotoService
) {
    companion object {
        const val COVER_PATH_MIDDLE = "profile/covers"
        const val AVATAR_PATH_MIDDLE = "profile/avatars"
    }

    /**
     * Получить все профили по поисковому запросу.
     * @param query запрос в поиске
     */
    fun getProfilesBySearch(authorId: Int, query: String): List<SearchProfile> {
        return profileRepository.findAllMatches(authorId, query)
    }

    /**
     * Получить информацию профиля автора запроса.
     * Для пользователя.
     * В ситуациях, когда пользователь только авторизовался и нужно получить данные, чтобы положить в SQLlite.
     * @param userUUID uuid пользователя инициирующего запрос
     */
    fun getAuthorsProfile(userUUID: UUID): ProfileDTO {
        userRepository.findById(userUUID)
            .orElseThrow { WrongIdException("Не удалось найти пользователя по uuid") }

        return profileRepository.findByUserUUID(userUUID)
    }

    /**
     * Добавить новый профиль. При регистрации.
     * Для пользователя.
     * @param avatar картинка аватарки пользователя
     * @param mainData DTO нового профиля пользователя
     */
    fun createNewProfile(avatar: MultipartFile?, mainData: NewProfileRequest): ProfileEntity {
        userRepository.findById(mainData.userUUID)
            .orElseThrow { WrongIdException("Нет пользователя с таким uuid") }

        val avatarPath = avatar?.let { photoService.saveFile(it, avatarsPath, AVATAR_PATH_MIDDLE) }

        val newProfile = mainData.mapToProfile(avatarPath)
        return profileRepository.save(newProfile)
    }

    /**
     * Изменить информацию в профиле (по одному полю)
     * Для пользователя
     * @param avatar картинка аватарки пользователя
     * @param cover картинка шапки профиля пользователя
     * @param updProfile DTO для обновления данных профиля
     * @throws WrongIdException если нет пользователя с таким uuid
     */
    fun updateProfile(avatar: MultipartFile?, cover: MultipartFile?, updProfile: UpdateProfileRequest): ProfileEntity {
        val profile = profileRepository.findById(updProfile.id)
            .orElseThrow { throw WrongIdException("Нет профиля с таким id") }

        val newAvatarPath = avatar?.let {
            profile.avatar?.let { photoService.deleteFileIfExists(it) }
            photoService.saveFile(it, avatarsPath, AVATAR_PATH_MIDDLE)
        } ?: profile.avatar

        val newCoverPath = cover?.let {
            profile.coverPhoto?.let { photoService.deleteFileIfExists(it) }
            photoService.saveFile(it, coversPath, COVER_PATH_MIDDLE)
        } ?: profile.coverPhoto

        val updatedProfile = profile.copyChangedProperties(updProfile, newAvatarPath, newCoverPath)

        return profileRepository.save(updatedProfile)
    }

    /**
     * Обновить FCM токен пользователя.
     * @param newToken DTO для запроса на обновление токена по profileId
     * @throws WrongIdException если нет профиля по переданному id
     */
    fun updateToken(newToken: UpdateTokenRequest) {
        profileRepository.findById(newToken.profileId)
            .orElseThrow { WrongIdException("Не удалось найти профиль по id") }

        profileRepository.updateTokenByProfileId(newToken.profileId, newToken.fcmToken)
    }

    /**
     * Получить семь путешественников, у которых trip_number max
     * Для пользователя.
     */
    fun getInspiringTravellers(authorId: Int): List<InspiringProfileResponse> {
        return profileRepository.findOrderedByTripsNumber(authorId)
    }

    /**
     * Получить профиль другого пользователя по profileId.
     * Для пользователя.
     * @param profileId id профиля другого пользователя
     */
    fun getOtherUserProfile(profileId: Int): ProfileDTO {
        return profileRepository.findByProfileId(profileId)
    }

    /**
     * Получить обновление статы по путешествиям, подпискам и подписчикам.
     * Для пользователей
     * @param authorId id профиля автора запроса
     */
    fun getUpdatedStats(authorId: Int): UpdatedProfileStatsResponse {
        return profileRepository.findStatsByProfileId(authorId)
    }
}