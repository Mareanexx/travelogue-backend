package ru.mareanexx.travelogue.domain.notifications

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.notifications.dto.NotificationResponse
import ru.mareanexx.travelogue.domain.notifications.dto.RecipientDTO

@Repository
interface NotificationsRepository : CrudRepository<NotificationsEntity, Int> {
    @Query("""
        SELECT pr.id, pr.fcm_token, mp.trip_id
        FROM map_point mp
        JOIN trip ON mp.trip_id = trip.id
        JOIN profile pr ON trip.profile_id = pr.id
        WHERE mp.id = :mapPointId
    """)
    fun findNotificationRecipientProfileId(@Param("mapPointId") mapPointId: Int): RecipientDTO?

    @Query("""
        SELECT n.id, n.sender_id, pr.avatar, pr.username, n.type, n.related_trip_id,
            n.related_point_id, n.related_comment_id, n.is_read, n.created_at
        FROM notifications n
        JOIN profile pr ON pr.id = n.sender_id
        WHERE n.recipient_id = :recipientId
    """)
    fun findAllByRecipientId(@Param("recipientId") recipientId: Int): List<NotificationResponse>
}