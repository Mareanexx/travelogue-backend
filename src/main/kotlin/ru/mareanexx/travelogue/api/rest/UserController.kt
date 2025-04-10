package ru.mareanexx.travelogue.api.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mareanexx.travelogue.domain.user.UserEntity
import ru.mareanexx.travelogue.domain.user.UserService
import ru.mareanexx.travelogue.domain.user.dto.NewUserRequest
import ru.mareanexx.travelogue.domain.user.types.UserRole
import ru.mareanexx.travelogue.support.exceptions.EmailAlreadyExistsException
import ru.mareanexx.travelogue.support.exceptions.WrongIdException
import java.util.*
import javax.naming.NoPermissionException

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMINISTRATOR')")
    fun getAllUsersByRole(@RequestParam role: UserRole): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok(userService.getAllUsersByRole(role))
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMINISTRATOR')")
    fun deleteUser(@PathVariable uuid: UUID): ResponseEntity<String> {
        return try {
            userService.deleteUserByUuid(uuid)
            ResponseEntity.ok("Successfully delete user")
        } catch(e: Exception) {
            ResponseEntity.badRequest().body("User doesn't exist")
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    fun addNewUserAnyRole(@RequestBody newUserRequest: NewUserRequest): ResponseEntity<Map<String, String>> {
        return try {
            val newUserUUID = userService.addNewUser(newUserRequest)
            ResponseEntity(mapOf("uuid" to newUserUUID.toString()) , HttpStatus.CREATED)
        } catch (e: EmailAlreadyExistsException) {
            ResponseEntity.badRequest().body(mapOf("error" to "User with such email already exists"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to "Can't add new user"))
        }
    }

    @PatchMapping("/{uuid}/status")
    @PreAuthorize("hasRole('MODERATOR')")
    fun updateUserBlockedStatus(@PathVariable uuid: UUID, @RequestParam block: Boolean): ResponseEntity<String> {
        return try {
            userService.blockUser(uuid, block)
            ResponseEntity.ok("Success in blocking/unblocking user")
        } catch (e: WrongIdException) {
            ResponseEntity("User with such uuid wasn't found", HttpStatus.NOT_FOUND)
        } catch (e: NoPermissionException) {
            ResponseEntity("You have no permissions to block this user", HttpStatus.FORBIDDEN)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Can't block user")
        }
    }

    @GetMapping("/block")
    @PreAuthorize("hasRole('MODERATOR')")
    fun getAllBlocked(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok(userService.getAllBlocked())
    }
}