package ru.mareanexx.travelogue.domain.notifications

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.notifications.dto.NotificationResponse
import ru.mareanexx.travelogue.domain.notifications.dto.comment.NewCommentNotification
import ru.mareanexx.travelogue.domain.notifications.dto.follows.NewFollowsNotification
import ru.mareanexx.travelogue.domain.notifications.dto.likes.NewLikeNotification
import ru.mareanexx.travelogue.domain.notifications.dto.mappoint.NewMapPointNotification
import ru.mareanexx.travelogue.domain.notifications.dto.trip.NewTripNotification
import ru.mareanexx.travelogue.domain.notifications.mapper.mapToEntity
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import ru.mareanexx.travelogue.support.firebase.FirebaseMessagingService
import ru.mareanexx.travelogue.support.firebase.messages.*
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationsService(
    private val notificationsRepository: NotificationsRepository,
    private val fcmService: FirebaseMessagingService,
    private val profileRepository: ProfileRepository
) {
    /**
     * Добавить уведомление о комментарии.
     * От senderId к recipientId (profile_id)
     */
    @Async
    fun addNewCommentNotification(comment: NewCommentNotification) {
        try {
            val recipient = notificationsRepository.findNotificationRecipientProfileId(comment.mapPointId) ?: return

            val senderProfile = profileRepository.findById(comment.senderId).getOrNull() ?: return

            if (recipient.id == senderProfile.id!!) return

            notificationsRepository.save(comment.mapToEntity(recipient.id, recipient.tripId))

            // если нет токена, то уведомление не отправится!
            recipient.fcmToken?.let { token ->
                fcmService.sendCommentNotification(
                    NewCommentMessage(
                        token = token,
                        messageBody = "${senderProfile.username} commented on your map point",
                        mapPointId = comment.mapPointId,
                        tripId = recipient.tripId,
                        commentId = comment.commentId
                    )
                )
            }

        } catch (e: Exception) {
            println("Ошибка при отправке уведомления: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Добавить уведомление о новом лайке
     * От profileId отправителя к recipientId получателя.
     */
    @Async
    fun addNewLikeNotification(newLike: NewLikeNotification) {
        try {
            val recipient = notificationsRepository.findNotificationRecipientProfileId(newLike.mapPointId) ?: return

            val senderProfile = profileRepository.findById(newLike.senderId).getOrNull() ?: return

            if (recipient.id == senderProfile.id!!) return

            notificationsRepository.save(newLike.mapToEntity(recipient.id, recipient.tripId))

            // если нет токена, то уведомление не отправится!
            recipient.fcmToken?.let { token ->
                fcmService.sendLikeNotification(
                    NewLikeMessage(
                        token = token,
                        messageBody = "${senderProfile.username} liked your map point",
                        mapPointId = newLike.mapPointId,
                        tripId = recipient.tripId,
                    )
                )
            }

        } catch (e: Exception) {
            println("Ошибка при отправке уведомления: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Добавить уведомление о новом follower.
     */
    @Async
    fun addNewFollowerNotification(newFollows: NewFollowsNotification) {
        try {
            val recipient = profileRepository.findById(newFollows.followingId).getOrNull() ?: return

            val senderProfile = profileRepository.findById(newFollows.followerId).getOrNull() ?: return

            notificationsRepository.save(newFollows.mapToEntity())

            // если нет токена, то уведомление не отправится!
            recipient.fcmToken?.let { token ->
                fcmService.sendFollowsNotification(
                    NewFollowMessage(
                        token = token,
                        messageBody = "${senderProfile.username} just followed you",
                        followerId = newFollows.followerId
                    )
                )
            }
        } catch (e: Exception) {
            println("Ошибка при отправке уведомления: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Уведомить всех фолловеров о новом путешествии.
     */
    @Async
    fun notifyAllFollowersAboutNewTrip(newTrip: NewTripNotification) {
        try {
            val followers = profileRepository.findFollowersByProfileId(newTrip.creatorId)
            val creatorProfile = profileRepository.findById(newTrip.creatorId).getOrNull() ?: return

            if (followers.isEmpty()) return

            val entities = followers.map {
                NotificationsEntity(
                    recipientId = it.id,
                    senderId = newTrip.creatorId,
                    type = newTrip.type,
                    relatedTripId = newTrip.tripId,
                    relatedPointId = null,
                    relatedCommentId = null,
                    createdAt = LocalDateTime.now()
                )
            }
            notificationsRepository.saveAll(entities)

            val notificationMessages = followers.mapNotNull { follower ->
                val token = follower.fcmToken
                if (token != null) {
                    NewTripMessage(
                        token = token,
                        messageBody = "${creatorProfile.username} just created a new trip",
                        tripId = newTrip.tripId
                    )
                } else {
                    null
                }
            }
            if (notificationMessages.isEmpty()) return

            fcmService.sendAllNewTripNotifications(notificationMessages)

        } catch (e: Exception) {
            println("Ошибка при уведомлении фолловеров о новом trip: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Уведомить всех фолловеров о новом map_point
     */
    @Async
    fun notifyAllFollowersAboutNewMapPoint(newMapPoint: NewMapPointNotification) {
        try {
            val creatorProfile = profileRepository.findByTripId(newMapPoint.tripId) ?: return

            val followers = profileRepository.findFollowersByProfileId(creatorProfile.id!!)

            if (followers.isEmpty()) return

            val entities = followers.map {
                NotificationsEntity(
                    recipientId = it.id,
                    senderId = creatorProfile.id,
                    type = newMapPoint.type,
                    relatedTripId = newMapPoint.tripId,
                    relatedPointId = newMapPoint.mapPointId,
                    relatedCommentId = null,
                    createdAt = LocalDateTime.now()
                )
            }
            notificationsRepository.saveAll(entities)

            val notificationMessages = followers.mapNotNull { follower ->
                val token = follower.fcmToken
                if (token != null) {
                    NewMapPointMessage(
                        token = token,
                        messageBody = "${creatorProfile.username} just created a new map point in trip",
                        tripId = newMapPoint.tripId,
                        mapPointId = newMapPoint.mapPointId
                    )
                } else {
                    null
                }
            }

            if (notificationMessages.isEmpty()) return

            fcmService.sendAllNewMapPointNotifications(notificationMessages)

        } catch (e: Exception) {
            println("Ошибка при уведомлении фолловеров о новом map point: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Получить все уведомления, где пользователь является получателем (recipientId)
     * @throws WrongIdException если не найден профиль по предоставленному id
     */
    fun getAllByRecipientId(recipientId: Int): List<NotificationResponse> {
        profileRepository.findById(recipientId)
            .orElseThrow { WrongIdException("Не удалось найти profile по его id") }

        return notificationsRepository.findAllByRecipientId(recipientId)
    }
}