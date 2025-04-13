package ru.mareanexx.travelogue.domain.profile

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.mareanexx.travelogue.domain.profile.dto.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.profile.dto.ProfileDTO
import ru.mareanexx.travelogue.domain.profile.dto.stats.UpdatedProfileStatsResponse
import java.util.*

interface ProfileRepository: CrudRepository<ProfileEntity, Int> {
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
        SELECT p.id, p.username, p.full_name, p.bio, p.avatar, p.cover_photo, 
            p.followers_number, p.following_number, p.trips_number
        FROM "profile" p
        JOIN "user" u ON u.uuid = p.user_uuid
        WHERE u.status = 'Active'
        ORDER BY p.trips_number DESC
        LIMIT 7
    """)
    fun findOrderedByTripsNumber(): List<InspiringProfileResponse>

    @Query("""
        SELECT trips_number, followers_number, following_number
        FROM profile
        WHERE id = :authorId
    """)
    fun findStatsByProfileId(@Param("authorId") authorId: Int): UpdatedProfileStatsResponse
}