package ru.mareanexx.travelogue.domain.map_point

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.map_point.dto.*
import ru.mareanexx.travelogue.domain.map_point.mapper.copyChangedProperties
import ru.mareanexx.travelogue.domain.map_point.mapper.mapToMapPoint
import ru.mareanexx.travelogue.domain.map_point.mapper.mapToResponse
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@Service
class MapPointService(
    private val tripRepository: TripRepository,
    private val mapPointRepository: MapPointRepository
) {
    /**
     * Добавить новый map_point прикрепив к trip.
     * Для пользователя.
     * @param newMapPoint DTO запроса на добавление нового map_point
     */
    fun addNewToTrip(newMapPoint: NewMapPointRequest, photosNumber: Int): UserMapPoint {
        tripRepository.findById(newMapPoint.tripId)
            .orElseThrow { WrongIdException("Не удалось найти trip по данному tripId") }

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
     * @param tripId id путешествия пользователя, к которому будут получаться map_points
     */
    fun getAllByTripId(tripId: Int): List<UserMapPoint> {
        return mapPointRepository.findAllByTripIdForUser(tripId)
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
    fun deleteMapPointById(mapPointId: Int) {
        mapPointRepository.findById(mapPointId)
            .orElseThrow { WrongIdException("Не удалось найти map_point по id") }

        return mapPointRepository.deleteById(mapPointId)
    }
}