package ru.mareanexx.travelogue.domain.likes

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mareanexx.travelogue.domain.likes.dto.LikeRequest
import ru.mareanexx.travelogue.domain.likes.mapper.mapToLike
import ru.mareanexx.travelogue.domain.likes.type.LikeStatusCode
import ru.mareanexx.travelogue.domain.map_point.MapPointRepository
import ru.mareanexx.travelogue.support.exceptions.IncrementException

@Service
class LikesService(
    private val likesRepository: LikesRepository,
    private val mapPointRepository: MapPointRepository
) {
    /**
     * Поставить лайк под map_point любого пользователя.
     * Для пользователя.
     * @param newLikeRequest DTO с запросом на добавление нового лайка
     */
    @Transactional
    fun addNew(newLikeRequest: LikeRequest): LikeStatusCode {
        val newLike = newLikeRequest.mapToLike()

        val rowsAffected = likesRepository.save(newLike)

        val updatedRows = mapPointRepository.incrementLikesNumber(newLike.mapPointId)
        if (updatedRows == 0) throw IncrementException("Не удалось обновить likes_number у mapPoint с id=${newLike.mapPointId}")

        return when (rowsAffected) {
            0 -> LikeStatusCode.ERROR
            1 -> LikeStatusCode.SUCCESS
            else -> LikeStatusCode.UNKNOWN
        }
    }

    /**
     * Удалить поставленный лайк.
     * Для пользователя.
     * @param profileId id профиля, который ставит лайк
     * @param mapPointId id точки на карте которой ставится лайк
     */
    @Transactional
    fun deleteExisted(profileId: Int, mapPointId: Int): LikeStatusCode {
        val rowsAffected = likesRepository.deleteByIds(profileId, mapPointId)

        val updatedRows = mapPointRepository.decrementLikesNumber(mapPointId)
        if (updatedRows == 0) throw IncrementException("Не удалось обновить likes_number у mapPoint с id=${mapPointId}")

        return when (rowsAffected) {
            0 -> LikeStatusCode.ERROR
            1 -> LikeStatusCode.SUCCESS
            else -> LikeStatusCode.UNKNOWN
        }
    }
}