package ru.mareanexx.travelogue.domain.notifications

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.notifications.dto.RecipientDTO

@Repository
interface NotificationsRepository : CrudRepository<NotificationsEntity, Int> {
    @Query("""
        SELECT pr.id AS profileId, pr.fcm_token, mp.trip_id
        FROM map_point mp
        JOIN trip ON mp.trip_id = trip.id
        JOIN profile pr ON trip.profile_id = pr.id
        WHERE mp.id = :mapPointId
    """)
    fun findNotificationRecipientProfileId(@Param("mapPointId") mapPointId: Int): RecipientDTO?
}