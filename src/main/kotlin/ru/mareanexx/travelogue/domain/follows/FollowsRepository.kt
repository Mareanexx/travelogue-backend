package ru.mareanexx.travelogue.domain.follows

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.mareanexx.travelogue.domain.follows.dto.FollowerDTO
import ru.mareanexx.travelogue.domain.follows.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.follows.dto.FollowingDTO

@Repository
class FollowsRepository(private val jdbcTemplate: JdbcTemplate) {
    fun save(followsEntity: FollowsEntity): Int {
        val sql = "INSERT INTO follows (follower_id, following_id, followed_at) VALUES (?, ?, ?)"
        return jdbcTemplate.update(sql, followsEntity.followerId, followsEntity.followingId, followsEntity.followedAt)
    }

    fun deleteByFollows(followerId: Int, followingId: Int): Int {
        val sql = "DELETE FROM follows WHERE follower_id = ? AND following_id = ?"
        return jdbcTemplate.update(sql, followerId, followingId)
    }

    fun findFollowersAndFollowings(profileId: Int): FollowersAndFollowingsResponse {
        // запрос для получения подписчиков пользователя
        val followersSql = """
            SELECT p.id, p.username, p.avatar, p.bio
            FROM profile p
            JOIN follows f ON f.follower_id = p.id
            WHERE f.following_id = ?
        """
        val followers = jdbcTemplate.query(
            followersSql,
            arrayOf(profileId)
        ) { rs, _ ->
            FollowerDTO(
                id = rs.getInt("id"),
                username = rs.getString("username"),
                avatar = rs.getString("avatar"),
                bio = rs.getString("bio")
            )
        }

        // запрос для получения всех подписок пользователя
        val followingsSql = """
            SELECT p.id, p.username, p.avatar, p.bio
            FROM profile p
            JOIN follows f ON f.following_id = p.id
            WHERE f.follower_id = ?
        """
        val followings = jdbcTemplate.query(
            followingsSql,
            arrayOf(profileId)
        ) { rs, _ ->
            FollowingDTO(
                id = rs.getInt("id"),
                username = rs.getString("username"),
                avatar = rs.getString("avatar"),
                bio = rs.getString("bio")
            )
        }

        return FollowersAndFollowingsResponse(followers, followings)
    }
}