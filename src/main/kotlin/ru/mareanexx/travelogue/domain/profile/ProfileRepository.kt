package ru.mareanexx.travelogue.domain.profile

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.mareanexx.travelogue.domain.notifications.dto.trip.FollowerWithFcm
import ru.mareanexx.travelogue.domain.profile.dto.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.profile.dto.ProfileDTO
import ru.mareanexx.travelogue.domain.profile.dto.stats.UpdatedProfileStatsResponse
import java.util.*

interface ProfileRepository: CrudRepository<ProfileEntity, Int> {
    @Modifying
    @Query("""
        UPDATE profile SET fcm_token = :token
        WHERE id = :profileId
    """)
    fun updateTokenByProfileId(
        @Param("profileId") profileId: Int,
        @Param("token") token: String?
    )

    @Query("""
        SELECT id, username, full_name, bio, avatar, cover_photo,
        followers_number, following_number, trips_number
        FROM profile
        WHERE user_uuid = :authorUuid
    """)
    fun findByUserUUID(@Param("authorUuid") authorUUID: UUID): ProfileDTO

    @Query("""
        SELECT id, username, full_name, bio, avatar, cover_photo,
        followers_number, following_number, trips_number
        FROM profile
        WHERE id = :profileId
    """)
    fun findByProfileId(@Param("profileId") profileId: Int): ProfileDTO

    @Query("""
        SELECT p.id, p.username, p.bio, p.avatar, p.cover_photo, 
            p.followers_number, p.following_number, p.trips_number,
            EXISTS (
               SELECT 1
               FROM follows f 
               WHERE f.follower_id = :authorId AND f.following_id = p.id
            ) AS isFollowing
        FROM "profile" p
        JOIN "user" u ON u.uuid = p.user_uuid
        WHERE u.status = 'Active' AND p.id != :authorId
        ORDER BY p.trips_number DESC
        LIMIT 7
    """)
    fun findOrderedByTripsNumber(@Param("authorId") authorId: Int): List<InspiringProfileResponse>

    @Query("""
        SELECT trips_number, followers_number, following_number
        FROM profile
        WHERE id = :authorId
    """)
    fun findStatsByProfileId(@Param("authorId") authorId: Int): UpdatedProfileStatsResponse


    @Query("""
        SELECT pr.id, pr.fcm_token
        FROM profile pr
        JOIN follows f ON pr.id = f.follower_id
        WHERE f.following_id = :creatorId
    """)
    fun findFollowersByProfileId(@Param("creatorId") creatorId: Int): List<FollowerWithFcm>

    @Query("""
        SELECT * FROM profile
        JOIN trip ON profile.id = trip.profile_id
        WHERE trip.id = :tripId
    """)
    fun findByTripId(@Param("tripId") tripId: Int): ProfileEntity?
}