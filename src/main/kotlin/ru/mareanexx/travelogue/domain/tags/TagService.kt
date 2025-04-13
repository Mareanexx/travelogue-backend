package ru.mareanexx.travelogue.domain.tags

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.tags.dto.TopTag
import ru.mareanexx.travelogue.domain.tags.mapper.mapToTags

@Service
class TagService(
    private val tagsRepository: TagsRepository
) {
    /**
     * Добавить новые теги при создании нового trip.
     * @param listTags список всех тегов
     * @param tripId id путешествия к которому прикрепляются теги
     */
    fun addNew(listTags: List<NewTag>, tripId: Int) {
        listTags.forEach { tag ->
            tagsRepository.save(tag.mapToTags(tripId))
        }
    }

    /**
     * Изменить теги, если изменились
     * @param listTags список измененных тегов
     * @param tripId id путешествия к которому прикреплены теги
     */
    fun editTags(listTags: List<NewTag>, tripId: Int) {
        tagsRepository.deleteAllByTripId(tripId)

        listTags.forEach { tag -> tagsRepository.save(TagsEntity(tripId = tripId, name = tag.name)) }
    }

    /**
     * Получить 8 самых часто встречаемых тегов (популярных)
     */
    fun getTop(): List<TopTag> {
        return tagsRepository.findTopEight()
    }
}