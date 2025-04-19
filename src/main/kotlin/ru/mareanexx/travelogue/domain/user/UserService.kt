package ru.mareanexx.travelogue.domain.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.user.dto.NewUserRequest
import ru.mareanexx.travelogue.domain.user.mapper.changeProps
import ru.mareanexx.travelogue.domain.user.types.UserRole
import ru.mareanexx.travelogue.domain.user.types.UserStatus
import ru.mareanexx.travelogue.support.exceptions.EmailAlreadyExistsException
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import java.util.*
import javax.naming.NoPermissionException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun deleteOwnAccount(uuid: UUID) {
        val user = userRepository.findById(uuid)
            .orElseThrow { WrongIdException("User with this UUID not found") }

        if (user.role != UserRole.User) {
            throw NoPermissionException("Only USER can delete themselves")
        }

        userRepository.deleteById(uuid)
    }

    fun updateEmail(uuid: UUID, newEmail: String) {
        val user = userRepository.findById(uuid)
            .orElseThrow { WrongIdException("User with this UUID not found") }

        if (user.role != UserRole.User) {
            throw NoPermissionException("Only USER can update their own email")
        }

        if (userRepository.findByEmail(newEmail) != null) {
            throw EmailAlreadyExistsException("Email already taken")
        }

        val newEmailUser = user.changeProps(newEmail)
        userRepository.save(newEmailUser)
    }

    /**
     * Получение всех пользователей системы по роли.
     * Для администратора и модератора.
     * @param role роль в системе (админ, пользователь, модератор); enum - UserRole
     */
    fun getAllUsersByRole(role: UserRole): List<UserEntity> {
        return userRepository.findAllByRole(role)
    }

    /**
     * Удалить пользователя по uuid.
     * Для администратора и пользователя (удалить свой аккаунт).
     * @param deletedUUID uuid удаляемого пользователя
     * @throws IllegalArgumentException если такого пользователя нет в системе
     */
    fun deleteUserByUuid(deletedUUID: UUID) {
        userRepository.deleteById(deletedUUID)
    }

    /**
     * Добавить нового пользователя в систему.
     * Для администратора.
     * @param newUserRequest DTO нового пользователя
     * @throws IllegalArgumentException если пользователь с таким email уже существует
     */
    fun addNewUser(newUserRequest: NewUserRequest): UUID? {
        if (userRepository.findByEmail(newUserRequest.email) != null) {
            throw EmailAlreadyExistsException("Пользователь с таким email уже существует")
        }

        val user = UserEntity(
            email = newUserRequest.email,
            passwordHash = passwordEncoder.encode(newUserRequest.password),
            role = newUserRequest.role,
            status = UserStatus.Active
        )
        val newUser = userRepository.save(user)
        return newUser.uuid
    }

    /**
     * Заблокировать/разблокировать пользователя.
     * Для модератора.
     * @param uuid uuid блокируемого пользователя, и actionType = block, unblock
     * @param isBlocking true если нужно заблокировать, false если разблокировка
     * @throws WrongIdException если пользователь по uuid не существует
     * @throws NoPermissionException если модератор пытается удалить админа или другого модератора.
     */
    fun blockUser(uuid: UUID, isBlocking: Boolean) {
        val blockedUser = userRepository.findById(uuid)
            .orElseThrow { WrongIdException("Пользователь по представленному uuid не найден") }
        if (blockedUser.role != UserRole.User)
            throw NoPermissionException("Нет прав на удаление модератора/администратора")

        blockedUser.status = when(isBlocking) {
            true -> UserStatus.Blocked
            else -> UserStatus.Active
        }

        userRepository.save(blockedUser)
    }

    /**
     * Получить всех заблокированных пользователей.
     * Для модератора.
     */
    fun getAllBlocked() = userRepository.findAllByStatusAndRole()
}