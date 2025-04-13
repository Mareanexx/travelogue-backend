package ru.mareanexx.travelogue.domain.likes

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.likes.dto.LikeRequest
import ru.mareanexx.travelogue.domain.likes.mapper.mapToLike
import ru.mareanexx.travelogue.domain.likes.type.LikeStatusCode

@Service
class LikesService(
    private val likesRepository: LikesRepository
) {
    /**
     * Поставить лайк под map_point любого пользователя.
     * Для пользователя.
     * @param newLikeRequest DTO с запросом на добавление нового лайка
     */
    fun addNew(newLikeRequest: LikeRequest): LikeStatusCode {
        val newLike = newLikeRequest.mapToLike()

        val rowsAffected = likesRepository.save(newLike)
        return when (rowsAffected) {
            0 -> LikeStatusCode.ERROR
            1 -> LikeStatusCode.SUCCESS
            else -> LikeStatusCode.UNKNOWN
        }
    }

    /**
     * Удалить поставленный лайк.
     * Для пользователя.
     * @param likesRequest DTO с запросом на удаление лайка
     */
    fun deleteExisted(likeRequest: LikeRequest): LikeStatusCode {
        val rowsAffected = likesRepository.deleteByIds(likeRequest.profileId, likeRequest.mapPointId)

        return when (rowsAffected) {
            0 -> LikeStatusCode.ERROR
            1 -> LikeStatusCode.SUCCESS
            else -> LikeStatusCode.UNKNOWN
        }
    }
}