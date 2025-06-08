package ru.mareanexx.travelogue.domain.trip

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.profile.dto.SearchTrip
import ru.mareanexx.travelogue.domain.trip.dto.TrendingTrip
import ru.mareanexx.travelogue.domain.trip.dto.TripWithoutTags

@Repository
interface TripRepository : CrudRepository<TripEntity, Int> {
    @Modifying
    @Query("UPDATE trip SET steps_number = steps_number + 1 WHERE id = :tripId")
    fun incrementStepsNumber(@Param("tripId") tripId: Int): Int

    @Modifying
    @Query("UPDATE trip SET steps_number = steps_number - 1 WHERE id = :tripId")
    fun decrementStepsNumber(@Param("tripId") tripId: Int): Int

    @Query("""
        SELECT tr.id, tr.cover_photo, tr.name, 
            pr.id AS profile_id, pr.avatar, pr.username 
        FROM trip tr
        JOIN profile pr ON tr.profile_id = pr.id
        WHERE (tr.name ILIKE '%' || :query || '%' OR tr.description ILIKE '%' || :query || '%') AND pr.id != :authorId
    """)
    fun findAllMatches(
        @Param("authorId") authorId: Int,
        @Param("query") query: String
    ): List<SearchTrip>

    @Query("""
        SELECT id, name, description, start_date, end_date, steps_number,
        days_number, type, status, cover_photo
        FROM "trip"
        WHERE profile_id = :authorId
    """)
    fun findAllByAuthorProfileId(@Param("authorId") authorId: Int): List<TripWithoutTags>

    @Query("""
        SELECT id, name, description, start_date, end_date, steps_number,
        days_number, type, status, cover_photo
        FROM "trip"
        WHERE profile_id = :profileId AND type = 'Public'
    """)
    fun findByProfileIdFilterByStatus(@Param("profileId") profileId: Int): List<TripWithoutTags>

    @Query("""
        SELECT tr.id, tr.name, tr.start_date,
               tr.steps_number, tr.days_number, tr.status, tr.cover_photo,
               pr.id AS profile_id, pr.username, pr.avatar
        FROM trip tr
        JOIN profile pr ON tr.profile_id = pr.id
        JOIN tags ON tags.trip_id = tr.id
        WHERE tags.name = :tagName AND pr.id != :finderId AND tr.type = 'Public'
    """)
    fun findAllByTag(
        @Param("tagName") tagName: String,
        @Param("finderId") finderId: Int
    ): List<TrendingTrip>

    @Query("""
        SELECT tr.id, tr.name, tr.start_date,
               tr.steps_number, tr.days_number, tr.status, tr.cover_photo,
               pr.id AS profile_id, pr.username, pr.avatar
        FROM trip tr
        JOIN profile pr ON pr.id = tr.profile_id
        JOIN follows f ON f.follower_id = :followerId AND f.following_id = pr.id
        WHERE tr.status = 'Current' AND tr.type = 'Public'
    """)
    fun findAllByStatusAndFollowerId(@Param("followerId") followerId: Int): List<TrendingTrip>


    @Query("""
        SELECT t.id, t.name, t.start_date, t.steps_number, t.days_number,
            t.status, t.cover_photo, p.id AS profile_id, p.username, p.avatar
        FROM trip t
        JOIN profile p ON p.id = t.profile_id
        JOIN map_point mp ON mp.trip_id = t.id
        WHERE t.type = 'Public' AND p.id != :authorId
        GROUP BY t.id, p.id
        ORDER BY SUM(mp.likes_number) DESC
        LIMIT 5
    """)
    fun findFiveByLikesNumber(@Param("authorId") authorId: Int): List<TrendingTrip>
}