package ru.mareanexx.travelogue.domain.follows

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.follows.dto.FollowUserRequest
import ru.mareanexx.travelogue.domain.follows.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.follows.mapper.mapToFollows
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@Service
class FollowsService(
    private val followsRepository: FollowsRepository,
    private val profileRepository: ProfileRepository
) {
    /**
     * Подписаться на profile другого пользователя
     * Для пользователя.
     * @param followUserRequest DTO запроса на подписку на профиль пользователя
     * @throws WrongIdException если какого либо профиля не существует
     */
    fun followNewProfile(followUserRequest: FollowUserRequest) {
        profileRepository.findById(followUserRequest.followerId)
            .orElseThrow { WrongIdException("Профиля пользователя-подписчика с таким id не существует") }

        profileRepository.findById(followUserRequest.followingId)
            .orElseThrow { WrongIdException("Профиля пользователя, на которого подписываются, с таким id не существует") }

        followsRepository.save(followUserRequest.mapToFollows())
    }

    /**
     * Отписаться от profile другого пользователя
     * Для пользователя.
     * @param followerId id пользователя, который подписывается
     * @param followingId id пользователя, на которого подписываются
     * @throws WrongIdException если какого либо профиля не существует
     */
    fun unfollowProfile(followerId: Int, followingId: Int) {
        profileRepository.findById(followerId)
            .orElseThrow { WrongIdException("Профиля пользователя-отписчика с таким id не существует") }

        profileRepository.findById(followingId)
            .orElseThrow { WrongIdException("Профиля пользователя, от которого отписываются, с таким id не существует") }

        followsRepository.deleteByFollows(followerId, followingId)
    }

    /**
     * Получить всех своих подписчиков и тех, на кого подписан сам.
     * Для пользователя.
     * @param authorId id профиля пользователя, который отправляет запрос
     * @param othersId id профиля пользователя, у которого нужно найти подписки
     */
    fun getFollowersAndFollowings(authorId: Int, othersId: Int): FollowersAndFollowingsResponse {
        profileRepository.findById(othersId)
            .orElseThrow { WrongIdException("Профиль с таким ID не найден") }

        return followsRepository.findFollowersAndFollowings(authorId, othersId)
    }

    /**
     * Проверить есть ли подписки у пользователя.
     * @param authorId id профиля автора запроса
     */
    fun checkFollowings(authorId: Int): Int {
        return followsRepository.countFollowings(authorId)
    }
}