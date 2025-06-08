package ru.mareanexx.travelogue.support.utils.test

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mareanexx.travelogue.domain.comment.CommentRepository
import ru.mareanexx.travelogue.domain.comment.mapper.mapToComment
import ru.mareanexx.travelogue.domain.follows.FollowsRepository
import ru.mareanexx.travelogue.domain.follows.mapper.mapToFollows
import ru.mareanexx.travelogue.domain.likes.LikesRepository
import ru.mareanexx.travelogue.domain.likes.mapper.mapToLike
import ru.mareanexx.travelogue.domain.map_point.MapPointRepository
import ru.mareanexx.travelogue.domain.point_photo.PointPhotoEntity
import ru.mareanexx.travelogue.domain.point_photo.PointPhotoRepository
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.tags.TagsEntity
import ru.mareanexx.travelogue.domain.tags.TagsRepository
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.user.UserService
import ru.mareanexx.travelogue.support.utils.test.mapper.toEntity

@Service
class TestDataService(
    private val userService: UserService,
    private val profileRepository: ProfileRepository,
    private val tripRepository: TripRepository,
    private val mapPointRepository: MapPointRepository,
    private val pointPhotoRepository: PointPhotoRepository,
    private val followsRepository: FollowsRepository,
    private val likesRepository: LikesRepository,
    private val commentRepository: CommentRepository,
    private val tagsRepository: TagsRepository
) {
    @Transactional
    fun insertTestData() {
        try {
            insertUsers()
            insertComments()
            insertFollows()
            insertLikes()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Test data inserted successfully")
    }

    fun insertUsers() {
        TestDataDB.usersData.forEach { item ->
            val insertedUserUuid = userService.addNewUser(item.user)!!
            val profile = profileRepository.save(item.profile.toEntity(insertedUserUuid))
            val trips = tripRepository.saveAll(
                item.trips.map { it.toEntity(profileId = profile.id!!) }
            ).toList()
            item.trips.zip(trips).forEach { (tripRequest, tripEntity) ->
                val tags = tripRequest.tagList.map { tag ->
                    TagsEntity(
                        tripId = tripEntity.id!!,
                        name = tag.name
                    )
                }
                tagsRepository.saveAll(tags)
            }
            val mapPoints = mapPointRepository.saveAll(item.mapPoints.map { it.toEntity(trips[0].id!!) })
            item.mapPoints.zip(mapPoints).forEach { (mapPointRequest, mapPointEntity) ->
                val photos = mapPointRequest.photosPath.map { photoPath ->
                    PointPhotoEntity(
                        mapPointId = mapPointEntity.id!!,
                        filePath = photoPath
                    )
                }
                pointPhotoRepository.saveAll(photos)
            }
        }
    }

    fun insertComments() {
        commentRepository.saveAll(TestDataDB.commentsData.map { it.mapToComment() })
    }

    fun insertLikes() {
        likesRepository.saveAll(TestDataDB.likesData.map { it.mapToLike() })
    }

    fun insertFollows() {
        followsRepository.saveAll(TestDataDB.followsData.map { it.mapToFollows() })
    }
}