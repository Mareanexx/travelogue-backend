package ru.mareanexx.travelogue.domain.comment

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.comment.dto.CommentResponse
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentRequest
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentResponse
import ru.mareanexx.travelogue.domain.comment.mapper.mapToComment
import ru.mareanexx.travelogue.domain.comment.mapper.mapToResponse
import ru.mareanexx.travelogue.domain.map_point.MapPointRepository
import ru.mareanexx.travelogue.support.exceptions.IncrementException
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val mapPointRepository: MapPointRepository
) {
    /**
     * Получение всех комментариев для отображения в деталях MapPoint
     * Для пользователя
     * @param mapPointId id конкретного пункта на карте
     */
    fun getAllByMapPointIdForUser(mapPointId: Int): List<CommentResponse> {
        return commentRepository.findAllByMapPointId(mapPointId)
    }

    /**
     * Добавление нового комментария под MapPoint
     * Для пользователя
     */
    fun addNewComment(newCommentRequest: NewCommentRequest): NewCommentResponse {
        val newComment = newCommentRequest.mapToComment()

        val updatedRows = mapPointRepository.incrementCommentsNumber(newComment.mapPointId)
        if (updatedRows == 0) throw IncrementException("Не удалось обновить comments_number у mapPoint с id=${newComment.mapPointId}")

        return commentRepository.save(newComment).mapToResponse()
    }

    /**
     * Получение всех комментариев для модератора
     * @param mapPointId id конкретного пункта на карте
     */
    fun getAllByMapPointForModerator(mapPointId: Int): List<CommentEntity> {
        return commentRepository.findAllByMapPointIdForModerator(mapPointId)
    }

    /**
     * Удаление комментария.
     * Для модератора и пользователя
     * @param commentId id удаляемого комментария
     */
    fun deleteByCommentId(commentId: Int) {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { WrongIdException("Не удалось найти comment по id") }

        val updatedRows = mapPointRepository.decrementCommentsNumber(comment.mapPointId)
        if (updatedRows == 0) throw IncrementException("Не удалось обновить comments_number у mapPoint с id=${comment.mapPointId}")

        commentRepository.deleteById(commentId)
    }
}