package ru.mareanexx.travelogue.domain.trip

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.trip.dto.*
import ru.mareanexx.travelogue.domain.trip.mapper.copyChangedProperties
import ru.mareanexx.travelogue.domain.trip.mapper.mapToResponse
import ru.mareanexx.travelogue.domain.trip.mapper.mapToTrip
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import ru.mareanexx.travelogue.support.utils.PhotoService


@Service
class TripService(
    @Value("\${app.uploads.trip-path}") private val coversPath: String,
    private val tripRepository: TripRepository,
    private val profileRepository: ProfileRepository,
    private val photoService: PhotoService
) {
    companion object {
        const val COVER_PATH_MIDDLE = "trip/covers"
    }

    /**
     * Получить путешествия пользователя автора запроса.
     * Данные идут для заполнения профиля.
     * @param authorId id профиля пользователя инициатора запроса
     */
    fun getAuthorsTrips(authorId: Int): List<AuthorTrip> {
        return tripRepository.findAllByAuthorProfileId(authorId)
    }

    /**
     * Добавление нового путешествия.
     * Для пользователя
     * @param newTripRequest DTO для нового путешествия
     * @param cover главная картинка путешествия
     */
    fun createNewTrip(newTripRequest: NewTripRequest, cover: MultipartFile): TripResponse {
        profileRepository.findById(newTripRequest.profileId)
            .orElseThrow { WrongIdException("Не удалось найти profile по profileId. Не удалось сохранить новый trip") }

        val coverPath = photoService.saveFile(cover, coversPath, COVER_PATH_MIDDLE)

        val newTrip = tripRepository.save(newTripRequest.mapToTrip(coverPath))
        return newTrip.mapToResponse()
    }

    /**
     * Удаление существующего путешествия. Также удаляется картинка
     * Для пользователя и для модератора
     * @param deletedTripId id путешествия
     * @throws WrongIdException если не удалось найти путешествие по id
     */
    fun deleteTrip(deletedTripId: Int) {
        val deletedTrip = tripRepository.findById(deletedTripId)
            .orElseThrow { WrongIdException("Не удалось найти trip по данному tripId") }

        photoService.deleteFileIfExists(deletedTrip.coverPhoto)

        tripRepository.delete(deletedTrip)
    }

    /**
     * Изменение каких-либо полей в путешествии.
     * Для пользователя
     * @param editTripRequest DTO для запроса на изменение
     * @throws WrongIdException когда не существует trip по tripId
     */
    fun editTrip(editTripRequest: EditTripRequest, cover: MultipartFile?): TripResponse {
        val existingTrip = tripRepository.findById(editTripRequest.id)
            .orElseThrow { WrongIdException("Не удалось найти trip с таким tripId. trip не будет отредактирован") }

        val newCoverPath = cover?.let {
            photoService.deleteFileIfExists(existingTrip.coverPhoto)
            photoService.saveFile(it, coversPath, "trip/covers")
        } ?: existingTrip.coverPhoto

        val editedTrip = existingTrip.copyChangedProperties(editTripRequest, newCoverPath)

        val trip = tripRepository.save(editedTrip)
        return trip.mapToResponse()
    }

    /**
     * Получить все trip по определенному тегу
     * @param tripByTag DTO с profileId (тот кто ищет записи) и tagName
     */
    fun getAllByTag(tripByTag: TripByTagRequest): List<TripByTag> {
        return tripRepository.findAllByTag(tripByTag.tagName, tripByTag.finderId)
    }

    /**
     * Получить все public путешествия другого пользователя.
     * @param profileId id профиля другого пользователя
     */
    fun getAllPublicOthersTrips(profileId: Int): List<UserTrip> {
        return tripRepository.findByProfileIdFilterByStatus(profileId)
    }

    /**
     * Получить все 'Current' путешествия путешественников, на которых подписан пользователь.
     * Для пользователя.
     * @param userProfile id пользователя, который делает запрос активности
     */
    fun getAllFollowingsCurrentTrips(userProfile: Int): List<ActiveFollowingTrip> {
        return tripRepository.findAllByStatusAndFollowerId(userProfile)
    }

    /**
     * Получить 5 самых залайканных путешествий.
     * Количество лайков определяется по map points
     */
    fun getFiveMostlyLikedTrips(authorId: Int): List<TrendingTrip> {
        return tripRepository.findFiveByLikesNumber(authorId)
    }
}