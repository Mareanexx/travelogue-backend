package ru.mareanexx.travelogue.domain.comment

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.comment.dto.CommentResponse

@Repository
interface CommentRepository : CrudRepository<CommentEntity, Int> {

    @Query("""
        SELECT c.id, c.text, c.send_date, c.sender_profile_id, 
            pr.username, pr.avatar
        FROM comment c
        JOIN profile pr ON pr.id = c.sender_profile_id 
        WHERE map_point_id = :mapPointId
    """)
    fun findAllByMapPointId(@Param("mapPointId") mapPointId: Int): List<CommentResponse>

    @Query("""
        SELECT *
        FROM comment
        WHERE map_point_id = :mapPointId
    """)
    fun findAllByMapPointIdForModerator(@Param("mapPointId") mapPointId: Int): List<CommentEntity>
}