package ru.mareanexx.travelogue.domain.point_photo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.mareanexx.travelogue.domain.map_point.dto.DeletedPhoto
import ru.mareanexx.travelogue.domain.point_photo.dto.PointPhotoDTO
import ru.mareanexx.travelogue.support.utils.PhotoService

@Service
class PointPhotoService(
    @Value("\${app.uploads.point-photo-path}") private val pointPhotoPath: String,
    private val pointPhotoRepository: PointPhotoRepository,
    private val photoService: PhotoService
) {
    companion object {
        const val COVER_PATH_MIDDLE = "mappoints/photos"
    }

    /**
     * Добавить новые фотографии map_point пользователя
     * @param mapPointId id созданного map_point
     * @param photos список переданных фотографий пользователя
     */
    fun addNewToMapPointId(mapPointId: Int, photos: List<MultipartFile>): List<PointPhotoDTO> {
        if (photos.isEmpty()) return emptyList()

        val pointPhotoList = mutableListOf<PointPhotoEntity>()
        photos.forEach { photo ->
            val filePath = photoService.saveFile(photo, pointPhotoPath, COVER_PATH_MIDDLE)
            pointPhotoList += PointPhotoEntity(filePath = filePath, mapPointId = mapPointId)
        }

        return pointPhotoRepository.saveAll(pointPhotoList).map { PointPhotoDTO(it.id!!, it.filePath, it.mapPointId) }
    }

    /**
     * Удалить все фотографии, прикрепленные к map_point
     * @param mapPointId id map_point, у которой нужно удалить фото
     */
    fun deleteAllByMapPointId(mapPointId: Int) {
        val foundPhotos = pointPhotoRepository.findAllById(mapPointId)
        if (foundPhotos.isEmpty()) return

        foundPhotos.forEach { photo ->
            photoService.deleteFileIfExists(photo.filePath)
        }

        pointPhotoRepository.deleteAllByMapPointId(mapPointId)
    }

    /**
     * Удалить все фотографии по пути
     * @param deletedPhotos список путей фото, которые нужно удалить
     */
    fun deletePhotosByFilePath(deletedPhotos: List<DeletedPhoto>) {
        if (deletedPhotos.isEmpty()) return

        deletedPhotos.forEach { photo ->
            photoService.deleteFileIfExists(photo.filePath)
        }

        pointPhotoRepository.deleteAllById(deletedPhotos.map { it.id })
    }

    /**
     * Получить все point_photos для каждого map_point путешествия
     * @param mapPoints все полученные map_points из другого сервиса
     */
    fun getAllByTripId(tripId: Int): List<PointPhotoDTO> {
        return pointPhotoRepository.findAllByTripId(tripId)
    }
}