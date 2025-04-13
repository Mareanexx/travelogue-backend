package ru.mareanexx.travelogue.domain.point_photo

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.point_photo.dto.PointPhotoDTO

@Repository
interface PointPhotoRepository: CrudRepository<PointPhotoEntity, Int> {
    fun deleteAllByMapPointId(mapPointId: Int)

    @Query("""
        SELECT *
        FROM "point_photo"
        WHERE map_point_id = :mapPointId
    """)
    fun findAllById(@Param("mapPointId") mapPointId: Int): List<PointPhotoEntity>

    @Query("""
        SELECT ph.id, ph.file_path, ph.map_point_id
        FROM point_photo ph
        JOIN "map_point" mp ON mp.id = ph.map_point_id
        WHERE mp.trip_id = :tripId
    """)
    fun findAllByTripId(@Param("tripId") tripId: Int): List<PointPhotoDTO>
}