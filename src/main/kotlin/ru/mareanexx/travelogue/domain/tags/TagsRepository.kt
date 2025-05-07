package ru.mareanexx.travelogue.domain.tags

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mareanexx.travelogue.domain.tags.dto.TagResponse
import ru.mareanexx.travelogue.domain.tags.dto.TopTag

@Repository
interface TagsRepository : CrudRepository<TagsEntity, Int> {
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM tags
        WHERE trip_id = :tripId
    """)
    fun deleteAllByTripId(tripId: Int)

    @Query("""
        SELECT id, name
        FROM "tags"
        WHERE trip_id = :tripId
    """)
    fun findAllByTripId(@Param("tripId") tripId: Int): List<TagResponse>

    @Query("""
        SELECT name
        FROM "tags"
        GROUP BY name
        ORDER BY COUNT(*) DESC
        LIMIT 8
    """)
    fun findTopEight(): List<TopTag>
}