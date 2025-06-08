package ru.mareanexx.travelogue.domain.follows

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.follows.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.follows.dto.Follows

@Repository
class FollowsRepository(private val jdbcTemplate: JdbcTemplate) {
    fun save(followsEntity: FollowsEntity): Int {
        val sql = "INSERT INTO follows (follower_id, following_id, followed_at) VALUES (?, ?, ?)"
        return jdbcTemplate.update(sql, followsEntity.followerId, followsEntity.followingId, followsEntity.followedAt)
    }

    fun saveAll(followsEntities: List<FollowsEntity>) {
        val sql = "INSERT INTO follows (follower_id, following_id, followed_at) VALUES (?, ?, ?)"
        jdbcTemplate.batchUpdate(sql, followsEntities, followsEntities.size) { ps, entity ->
            ps.setInt(1, entity.followerId)
            ps.setInt(2, entity.followingId)
            ps.setObject(3, entity.followedAt)
        }
    }

    fun countFollowings(authorId: Int): Int {
        var result = 0
        val sql = "SELECT COUNT(*) AS followingsNumber FROM follows WHERE follower_id = ?"
        jdbcTemplate.query(sql, arrayOf(authorId)) {
            println("Кароче нашли что followingsNumber = ${it.getInt("followingsNumber")}")
            result = it.getInt("followingsNumber")
        }
        return result
    }

    fun deleteByFollows(followerId: Int, followingId: Int): Int {
        val sql = "DELETE FROM follows WHERE follower_id = ? AND following_id = ?"
        return jdbcTemplate.update(sql, followerId, followingId)
    }

    fun findFollowersAndFollowings(authorId: Int, othersId: Int): FollowersAndFollowingsResponse {
        val authorFollowingsSql = """
        SELECT following_id FROM follows WHERE follower_id = ?
    """
        val authorFollowingIds: Set<Int> = jdbcTemplate.queryForList(
            authorFollowingsSql, arrayOf(authorId), Int::class.java
        ).toSet()

        val followersSql = """
        SELECT p.id, p.username, p.avatar, p.bio
        FROM profile p
        JOIN follows f ON f.follower_id = p.id
        WHERE f.following_id = ?
    """
        val followers = jdbcTemplate.query(
            followersSql,
            arrayOf(othersId)
        ) { rs, _ ->
            Follows(
                id = rs.getInt("id"),
                username = rs.getString("username"),
                avatar = rs.getString("avatar"),
                bio = rs.getString("bio"),
                isFollowing = authorFollowingIds.contains(rs.getInt("id"))
            )
        }

        val followingsSql = """
        SELECT p.id, p.username, p.avatar, p.bio
        FROM profile p
        JOIN follows f ON f.following_id = p.id
        WHERE f.follower_id = ?
    """
        val followings = jdbcTemplate.query(
            followingsSql,
            arrayOf(othersId)
        ) { rs, _ ->
            Follows(
                id = rs.getInt("id"),
                username = rs.getString("username"),
                avatar = rs.getString("avatar"),
                bio = rs.getString("bio"),
                isFollowing = authorFollowingIds.contains(rs.getInt("id"))
            )
        }

        return FollowersAndFollowingsResponse(followers = followers, followings = followings)
    }

}