package ru.mareanexx.travelogue.domain.map_point

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.map_point.dto.ModeratorMapPoint
import ru.mareanexx.travelogue.domain.map_point.dto.UpdatedMapPointStatsResponse
import ru.mareanexx.travelogue.domain.map_point.dto.UserMapPoint
import ru.mareanexx.travelogue.domain.map_point.projection.MapPointWithPreviewProjection

@Repository
interface MapPointRepository: CrudRepository<MapPointEntity, Int> {
    @Query("""
        SELECT id, name, description
        FROM "map_point"
        WHERE trip_id = :tripId
    """)
    fun findAllByTripIdForModerator(@Param("tripId") tripId: Int): List<ModeratorMapPoint>

    @Query(
        value = """
        SELECT mp.id, mp.longitude, mp.latitude, mp.arrival_date, mp.trip_id, pp.file_path AS preview_photo
        FROM map_point mp
        LEFT JOIN (
            SELECT DISTINCT ON (map_point_id) id, file_path, map_point_id
            FROM point_photo
            ORDER BY map_point_id, id
        ) pp ON mp.id = pp.map_point_id
        WHERE mp.trip_id IN (:tripIds)
    """)
    fun findAllWithPreviewPhotoByTripIds(@Param("tripIds") tripIds: List<Int>): List<MapPointWithPreviewProjection>

    @Query("""
        SELECT mp.id, mp.longitude, mp.latitude, mp.name, mp.description, mp.likes_number,
            mp.comments_number, mp.photos_number, mp.arrival_date, mp.trip_id,
            EXISTS (
               SELECT 1
               FROM likes l 
               WHERE l.profile_id = :authorId AND l.map_point_id = mp.id
            ) AS is_liked
        FROM "map_point" mp
        WHERE trip_id = :tripId
        ORDER BY arrival_date DESC
    """)
    fun findAllByTripIdForUser(
        @Param("authorId") authorId: Int,
        @Param("tripId") tripId: Int
    ): List<UserMapPoint>

    @Query("""
        SELECT id, likes_number, comments_number
        FROM "map_point"
        WHERE trip_id = :tripId
    """)
    fun findAllMapPointStatsByTripId(@Param("tripId") tripId: Int): List<UpdatedMapPointStatsResponse>
}