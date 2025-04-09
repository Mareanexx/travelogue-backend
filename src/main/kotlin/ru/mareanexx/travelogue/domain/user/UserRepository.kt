package ru.mareanexx.travelogue.domain.user

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : CrudRepository<UserEntity, UUID> {
    @Query("""
        SELECT *
        FROM "user"
        WHERE email = :email
    """)
    fun findByEmail(@Param("email") email: String): UserEntity?
}