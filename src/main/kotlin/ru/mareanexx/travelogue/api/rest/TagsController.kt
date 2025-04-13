package ru.mareanexx.travelogue.api.rest

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.travelogue.domain.tags.TagsRepository
import ru.mareanexx.travelogue.domain.tags.dto.TopTag

@RestController
@RequestMapping("/api/v1/tags")
class TagsController(
    private val tagsRepository: TagsRepository
) {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    fun getTop(): ResponseEntity<List<TopTag>> {
        return try {
            val topTagsList = tagsRepository.findTopEight()
            ResponseEntity.ok(topTagsList)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(emptyList())
        }
    }
}