package ru.mareanexx.travelogue.domain.tags

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.tags.dto.TagResponse
import ru.mareanexx.travelogue.domain.tags.dto.TopTag
import ru.mareanexx.travelogue.domain.tags.mapper.mapToTags
import ru.mareanexx.travelogue.domain.tags.mapper.toDto

@Service
class TagService(
    private val tagsRepository: TagsRepository
) {
    /**
     * Получить все теги путешествия
     * @param tripId id путешествия, у которого надо найти путешествия
     */
    fun getAllByTripId(tripId: Int) : List<TagResponse> {
        return tagsRepository.findAllByTripId(tripId)
    }

    /**
     * Добавить новые теги при создании нового trip.
     * @param listTags список всех тегов
     * @param tripId id путешествия к которому прикрепляются теги
     */
    fun addNew(listTags: List<NewTag>, tripId: Int): List<TagResponse> {
        return tagsRepository.saveAll(listTags.map { it.mapToTags(tripId) }).map { it.toDto() }
    }

    /**
     * Изменить теги, если изменились
     * @param listTags список измененных тегов
     * @param tripId id путешествия к которому прикреплены теги
     */
    fun editTags(listTags: List<NewTag>, tripId: Int): List<TagResponse> {
        tagsRepository.deleteAllByTripId(tripId)
        return addNew(listTags, tripId)
    }

    /**
     * Получить 8 самых часто встречаемых тегов (популярных)
     */
    fun getTop(): List<TopTag> {
        return tagsRepository.findTopEight()
    }
}