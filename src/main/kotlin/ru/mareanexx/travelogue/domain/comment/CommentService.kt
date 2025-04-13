package ru.mareanexx.travelogue.domain.comment

import org.springframework.stereotype.Service
import ru.mareanexx.travelogue.domain.comment.dto.CommentResponse
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentRequest
import ru.mareanexx.travelogue.domain.comment.dto.NewCommentResponse
import ru.mareanexx.travelogue.domain.comment.mapper.mapToComment
import ru.mareanexx.travelogue.domain.comment.mapper.mapToResponse
import ru.mareanexx.travelogue.support.exceptions.WrongIdException

@Service
class CommentService(
    private val commentRepository: CommentRepository
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
        commentRepository.findById(commentId)
            .orElseThrow { WrongIdException("Не удалось найти comment по id") }

        commentRepository.deleteById(commentId)
    }
}