package ru.mareanexx.travelogue.domain.map_point

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mareanexx.travelogue.domain.map_point.dto.*
import ru.mareanexx.travelogue.domain.map_point.mapper.copyChangedProperties
import ru.mareanexx.travelogue.domain.map_point.mapper.mapToMapPoint
import ru.mareanexx.travelogue.domain.map_point.mapper.mapToResponse
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.support.exceptions.IncrementException
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@Service
class MapPointService(
    private val tripRepository: TripRepository,
    private val mapPointRepository: MapPointRepository
) {
    fun getMapPointsGroupedByTripIds(tripIds: List<Int>): Map<Int, List<MapPointWithPhotoActivity>> {
        return mapPointRepository.findAllWithPreviewPhotoByTripIds(tripIds)
            .groupBy { it.tripId }
            .mapValues { (_, projections) ->
                projections.map { p ->
                    MapPointWithPhotoActivity(
                        id = p.id,
                        longitude = p.longitude,
                        latitude = p.latitude,
                        arrivalDate = p.arrivalDate,
                        tripId = p.tripId,
                        previewPhotoPath = p.previewPhoto
                    )
                }
            }
    }

    /**
     * Добавить новый map_point прикрепив к trip.
     * Для пользователя.
     * @param newMapPoint DTO запроса на добавление нового map_point
     */
    @Transactional
    fun addNewToTrip(newMapPoint: NewMapPointRequest, photosNumber: Int): UserMapPoint {
        tripRepository.findById(newMapPoint.tripId)
            .orElseThrow { WrongIdException("Не удалось найти trip по данному tripId") }

        val updatedRows = tripRepository.incrementStepsNumber(newMapPoint.tripId)
        if (updatedRows == 0) throw IncrementException("Не удалось обновить steps_number у trip с id=${newMapPoint.tripId}")

        return mapPointRepository.save(newMapPoint.mapToMapPoint(photosNumber)).mapToResponse()
    }

    /**
     * Изменить существующий map_point по map_point_id.
     * Для пользователя.
     * @param editMapPointRequest requestDTO для запроса на изменение полей в map_point
     * @throws WrongIdException если не найден trip по указанному tripId
     */
    fun editMapPoint(
        editMapPointRequest: EditMapPointRequest,
        changedPhotosNumber: (Int) -> Int
    ): UserMapPoint {
        val origMapPoint = mapPointRepository.findById(editMapPointRequest.id)
               .orElseThrow { WrongIdException("Не удалось найти map_point по id") }

        val editedMapPoint = origMapPoint.copyChangedProperties(
            editMapPointRequest,
            changedPhotosNumber(origMapPoint.photosNumber)
        )
        mapPointRepository.save(editedMapPoint)
        return editedMapPoint.mapToResponse()
    }

    /**
     * Получить все map_points путешествия (своего или другого пользователя).
     * @param authorId id профиля пользователя, который получает map points
     * @param tripId id путешествия пользователя, к которому будут получаться map_points
     */
    fun getAllByTripId(authorId: Int, tripId: Int): List<UserMapPoint> {
        return mapPointRepository.findAllByTripIdForUser(authorId, tripId)
    }

    /**
     * Получить обновленные статы map_point путешествия запросившего пользователя.
     * @param tripId id путешествия, к которому для всех его map_point нужны статы
     * @throws WrongIdException если не найден trip по указанному tripId
     */
    fun getAllMapPointsStats(tripId: Int): List<UpdatedMapPointStatsResponse> {
        tripRepository.findById(tripId)
            .orElseThrow { WrongIdException("Не удалось найти trip по данному tripId") }

        return mapPointRepository.findAllMapPointStatsByTripId(tripId)
    }

    /**
     * Получить все поля текстовые map_points путешествия по tripId
     * Для модератора.
     * @param tripId id путешествия у которого получаются map_points
     */
    fun getAllMapPointsByTripIdForModerator(tripId: Int): List<ModeratorMapPoint> {
        return mapPointRepository.findAllByTripIdForModerator(tripId)
    }

    /**
     * Удалить map_point по id
     * Для пользователя и модератора.
     * @param mapPointId id удаляемого map_point
     */
    @Transactional
    fun deleteMapPointById(mapPointId: Int) {
        val mapPoint = mapPointRepository.findById(mapPointId)
            .orElseThrow { WrongIdException("Не удалось найти map_point по id") }

        val updatedRows = tripRepository.decrementStepsNumber(mapPoint.tripId)
        if (updatedRows == 0) throw IncrementException("Не удалось обновить steps_number у trip с id=${mapPoint.tripId}")

        return mapPointRepository.delete(mapPoint)
    }
}