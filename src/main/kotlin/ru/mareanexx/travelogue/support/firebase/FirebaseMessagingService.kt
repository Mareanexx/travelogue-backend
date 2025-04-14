package ru.mareanexx.travelogue.support.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.support.firebase.messages.*

@Service
class FirebaseMessagingService {

    fun buildNotification(message: NewMessage): Notification {
        return Notification.builder()
            .setTitle(message.type.title)
            .setBody(message.messageBody)
            .build()
    }

    fun sendCommentNotification(commentMessage: NewCommentMessage) {
        val notification = buildNotification(commentMessage)

        val message = Message.builder()
            .setNotification(notification)
            .setToken(commentMessage.token)
            .putData("type", commentMessage.type.name)
            .putData("mapPointId", commentMessage.mapPointId.toString())
            .putData("tripId", commentMessage.tripId.toString())
            .putData("commentId", commentMessage.commentId.toString())
            .build()

        FirebaseMessaging.getInstance().send(message)
    }

    fun sendLikeNotification(likeMessage: NewLikeMessage) {
        val notification = buildNotification(likeMessage)

        val message = Message.builder()
            .setNotification(notification)
            .setToken(likeMessage.token)
            .putData("type", likeMessage.type.name)
            .putData("mapPointId", likeMessage.mapPointId.toString())
            .putData("tripId", likeMessage.tripId.toString())
            .build()

        FirebaseMessaging.getInstance().send(message)
    }

    fun sendFollowsNotification(followMessage: NewFollowMessage) {
        val notification = buildNotification(followMessage)

        val message = Message.builder()
            .setNotification(notification)
            .setToken(followMessage.token)
            .putData("type", followMessage.type.name)
            .putData("followerId", followMessage.followerId.toString())
            .build()

        FirebaseMessaging.getInstance().send(message)
    }

    fun sendAllNewTripNotifications(messages: List<NewTripMessage>) {
        try {
            val firebaseMessages = messages.map { tripMessage ->
                val notification = buildNotification(tripMessage)
                Message.builder()
                    .setNotification(notification)
                    .setToken(tripMessage.token)
                    .putData("type", "NewTrip")
                    .putData("tripId", tripMessage.tripId.toString())
                    .build()
            }

            FirebaseMessaging.getInstance().sendEach(firebaseMessages)
        } catch (e: Exception) {
            println("Ошибка при отправке всех уведомлений о новом путешествии: ${e.message}")
            e.printStackTrace()
        }
    }

    fun sendAllNewMapPointNotifications(messages: List<NewMapPointMessage>) {
        try {
            val firebaseMessages = messages.map { mapPointMessage ->
                val notification = buildNotification(mapPointMessage)
                Message.builder()
                    .setNotification(notification)
                    .setToken(mapPointMessage.token)
                    .putData("type", "NewMapPoint")
                    .putData("tripId", mapPointMessage.tripId.toString())
                    .putData("mapPointId", mapPointMessage.mapPointId.toString())
                    .build()
            }

            FirebaseMessaging.getInstance().sendEach(firebaseMessages)
        } catch (e: Exception) {
            println("Ошибка при отправке всех уведомлений о новом map point: ${e.message}")
            e.printStackTrace()
        }
    }

}