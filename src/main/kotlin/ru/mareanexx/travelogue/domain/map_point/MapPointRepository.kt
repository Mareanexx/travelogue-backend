package ru.mareanexx.travelogue.domain.map_point

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.map_point.dto.ModeratorMapPoint
import ru.mareanexx.travelogue.domain.map_point.dto.UpdatedMapPointStatsResponse
import ru.mareanexx.travelogue.domain.map_point.dto.UserMapPoint

@Repository
interface MapPointRepository: CrudRepository<MapPointEntity, Int> {
    @Query("""
        SELECT id, name, description
        FROM "map_point"
        WHERE trip_id = :tripId
    """)
    fun findAllByTripIdForModerator(@Param("tripId") tripId: Int): List<ModeratorMapPoint>

    @Query("""
        SELECT id, longitude, latitude, name, description, likes_number,
            comments_number, photos_number, arrival_date, trip_id
        FROM "map_point"
        WHERE trip_id = :tripId
        ORDER BY arrival_date DESC
    """)
    fun findAllByTripIdForUser(@Param("tripId") tripId: Int): List<UserMapPoint>

    @Query("""
        SELECT id, likes_number, comments_number
        FROM "map_point"
        WHERE trip_id = :tripId
    """)
    fun findAllMapPointStatsByTripId(@Param("tripId") tripId: Int): List<UpdatedMapPointStatsResponse>
}