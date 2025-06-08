package ru.mareanexx.travelogue.domain.likes

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class LikesRepository(private val jdbcTemplate: JdbcTemplate) {
    fun save(likesEntity: LikesEntity): Int {
        val sql = "INSERT INTO likes (profile_id, map_point_id) VALUES (?, ?)"
        return jdbcTemplate.update(sql, likesEntity.profileId, likesEntity.mapPointId)
    }

    fun saveAll(likesEntities: List<LikesEntity>) {
        val sql = "INSERT INTO likes (profile_id, map_point_id) VALUES (?, ?)"
        jdbcTemplate.batchUpdate(sql, likesEntities, likesEntities.size) { ps, entity ->
            ps.setInt(1, entity.profileId)
            ps.setInt(2, entity.mapPointId)
        }
    }

    fun deleteByIds(profileId: Int, mapPointId: Int): Int {
        val sql = "DELETE FROM likes WHERE profile_id = ? AND map_point_id = ?"
        return jdbcTemplate.update(sql, profileId, mapPointId)
    }
}