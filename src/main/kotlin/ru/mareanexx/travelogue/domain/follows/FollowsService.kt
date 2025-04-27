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
     * @param profileId id профиля пользователя, который получает данные
     */
    fun getFollowersAndFollowings(profileId: Int): FollowersAndFollowingsResponse {
        profileRepository.findById(profileId)
            .orElseThrow { WrongIdException("Профиль с таким ID не найден") }

        return followsRepository.findFollowersAndFollowings(profileId)
    }
}