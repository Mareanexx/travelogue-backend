package ru.mareanexx.travelogue.support.utils.test

import ru.mareanexx.travelogue.domain.comment.dto.NewCommentRequest
import ru.mareanexx.travelogue.domain.follows.dto.FollowUserRequest
import ru.mareanexx.travelogue.domain.likes.dto.LikeRequest
import ru.mareanexx.travelogue.domain.tags.dto.NewTag
import ru.mareanexx.travelogue.domain.trip.types.TripTimeStatus
import ru.mareanexx.travelogue.domain.trip.types.TripVisibilityType
import ru.mareanexx.travelogue.domain.user.dto.NewUserRequest
import ru.mareanexx.travelogue.domain.user.types.UserRole
import ru.mareanexx.travelogue.support.utils.test.requests.TestMapPointRequest
import ru.mareanexx.travelogue.support.utils.test.requests.TestProfileRequest
import ru.mareanexx.travelogue.support.utils.test.requests.TestTripRequest
import java.time.LocalDate
import java.time.OffsetDateTime

data class TestData(
    val user: NewUserRequest,
    val profile: TestProfileRequest,
    val trips: List<TestTripRequest>,
    val mapPoints: List<TestMapPointRequest>
)

object TestDataDB {
    val usersData = listOf(
        TestData(
            user = NewUserRequest("alice@example.com", "Secret123", UserRole.User),
            profile = TestProfileRequest(
                username = "Alice Wonder",
                fullName = "Alice Wonderland",
                bio = "Explorer of places and dreams.",
                avatar = "/uploads/profile/avatars/alice_wonder.jpg",
                cover = "/uploads/profile/covers/alice_wonder.jpg",
                followersNumber = 5,
                followingNumber = 5,
                tripsNumber = 1
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Alpine Adventure",
                    description = "Climbing and hiking in the Alps.",
                    startDate = LocalDate.parse("2024-07-15"),
                    endDate = LocalDate.parse("2024-07-25"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Past,
                    coverPhoto = "/uploads/trip/covers/alpine_adventure.jpg",
                    stepsNumber = 2,
                    daysNumber = 11,
                    tagList = listOf(
                        NewTag("Mountains"),
                        NewTag("Hiking")
                    )
                )
            ),
            mapPoints = listOf(
                TestMapPointRequest(
                    longitude = 10.2921,
                    latitude = 46.9207,
                    name = "St. Anton am Arlberg",
                    description = "Starting point of the alpine hiking route.",
                    arrivalDate = OffsetDateTime.parse("2024-07-15T10:00:00+02:00"),
                    tripId = 1,
                    likesNumber = 6,
                    commentsNumber = 3,
                    photosNumber = 2,
                    photosPath = listOf(
                        "/uploads/mappoints/photos/alpine_1_1.jpg",
                        "/uploads/mappoints/photos/alpine_1_2.jpg"
                    )
                ),
                TestMapPointRequest(
                    longitude = 8.0414,
                    latitude = 46.6102,
                    name = "Zermatt",
                    description = "Beautiful mountain village near the Matterhorn.",
                    arrivalDate = OffsetDateTime.parse("2024-07-20T15:00:00+02:00"),
                    tripId = 1,
                    likesNumber = 3,
                    commentsNumber = 4,
                    photosNumber = 2,
                    photosPath = listOf(
                        "/uploads/mappoints/photos/alpine_2_1.jpg",
                        "/uploads/mappoints/photos/alpine_2_2.jpg"
                    )
                )
            )
        ),
        TestData(
            user = NewUserRequest("bob@example.com", "MyPass456", UserRole.User),
            profile = TestProfileRequest(
                username = "Bob The Brave",
                fullName = "Bob Bravestone",
                bio = "Adventurer, always looking for new experiences.",
                avatar = "/uploads/profile/avatars/bob_the_brave.jpg",
                cover = "/uploads/profile/covers/bob_the_brave.jpg",
                followersNumber = 1,
                followingNumber = 3,
                tripsNumber = 1
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Weekend in Prague",
                    description = "Chill weekend with cafes, music, and museums.",
                    startDate = LocalDate.parse("2024-10-10"),
                    endDate = LocalDate.parse("2024-10-13"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Past,
                    coverPhoto = "/uploads/trip/covers/weekend_in_prague.jpg",
                    stepsNumber = 2,
                    daysNumber = 4,
                    tagList = listOf(
                        NewTag("Europe"),
                        NewTag("Culture")
                    )
                )
            ),
            mapPoints = listOf(
                TestMapPointRequest(
                    longitude = 14.4006,
                    latitude = 50.0870,
                    name = "Old Town Square",
                    description = "Heart of the historic city with cafés and street musicians.",
                    arrivalDate = OffsetDateTime.parse("2024-10-10T12:00:00+02:00"),
                    tripId = 2,
                    likesNumber = 5,
                    commentsNumber = 4,
                    photosNumber = 0,
                    photosPath = listOf(
                        "/uploads/mappoints/photos/prague_1_1.jpg",
                        "/uploads/mappoints/photos/prague_1_2.jpg"
                    )
                ),
                TestMapPointRequest(
                    longitude = 14.4036,
                    latitude = 50.0905,
                    name = "Charles Bridge",
                    description = "Scenic walk with views over the Vltava River.",
                    arrivalDate = OffsetDateTime.parse("2024-10-11T10:30:00+02:00"),
                    tripId = 2,
                    likesNumber = 6,
                    commentsNumber = 5,
                    photosNumber = 0,
                    photosPath = listOf(
                        "/uploads/mappoints/photos/prague_2_1.jpg",
                        "/uploads/mappoints/photos/prague_2_2.jpg"
                    )
                )
            )
        ),
        TestData(
            user = NewUserRequest("carol@example.com", "StrongPwd1", UserRole.User),
            profile = TestProfileRequest(
                username = "Carol Swift",
                fullName = "Carol Swifton",
                bio = "Photographer and travel writer.",
                avatar = "/uploads/profile/avatars/carol_swift.png",
                cover = "/uploads/profile/covers/carol_swift.jpg",
                followersNumber = 2,
                followingNumber = 5,
                tripsNumber = 2
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Desert Mirage",
                    description = "Exploring the Moroccan Sahara.",
                    startDate = LocalDate.parse("2023-11-01"),
                    endDate = LocalDate.parse("2023-11-10"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Past,
                    coverPhoto = "/uploads/trip/covers/desert_mirage.jpg",
                    stepsNumber = 0,
                    daysNumber = 10,
                    tagList = listOf(
                        NewTag("Desert"),
                        NewTag("Adventure")
                    )
                ),
                TestTripRequest(
                    name = "Bali Retreat",
                    description = "Spiritual and yoga journey through Bali.",
                    startDate = LocalDate.parse("2025-06-01"),
                    endDate = LocalDate.parse("2025-06-14"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Current,
                    coverPhoto = "/uploads/trip/covers/bali_retreat.jpg",
                    stepsNumber = 0,
                    daysNumber = 15,
                    tagList = listOf(
                        NewTag("Asia"),
                        NewTag("Relaxation")
                    )
                )
            ),
            mapPoints = emptyList()
        ),
        TestData(
            user = NewUserRequest("dave@example.com", "Qwerty789", UserRole.User),
            profile = TestProfileRequest(
                username = "Dave Journey",
                fullName = "Dave Johnson",
                bio = "Hiker, climber, and map enthusiast.",
                avatar = "/uploads/profile/avatars/dave_journey.png",
                cover = "/uploads/profile/covers/dave_journey.jpg",
                followersNumber = 4,
                followingNumber = 3,
                tripsNumber = 1
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Iceland Roadtrip",
                    description = "Ring Road journey with waterfalls and geysers.",
                    startDate = LocalDate.parse("2023-08-05"),
                    endDate = LocalDate.parse("2023-08-20"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Past,
                    coverPhoto = "/uploads/trip/covers/iceland_roadtrip.jpg",
                    stepsNumber = 0,
                    daysNumber = 16,
                    tagList = listOf(
                        NewTag("Nature"),
                        NewTag("Roadtrip")
                    )
                )
            ),
            mapPoints = emptyList()
        ),
        TestData(
            user = NewUserRequest("eve@example.com", "HelloWorld2", UserRole.User),
            profile = TestProfileRequest(
                username = "Eve Bright",
                fullName = "Evelyn Bright",
                bio = "Chasing sunsets and stories.",
                avatar = "/uploads/profile/avatars/eve_bright.png",
                cover = "/uploads/profile/covers/eve_bright.jpg",
                followersNumber = 4,
                followingNumber = 0,
                tripsNumber = 1
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Tokyo Lights",
                    description = "Exploring the vibrant city of Tokyo.",
                    startDate = LocalDate.parse("2025-05-10"),
                    endDate = null,
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Current,
                    coverPhoto = "/uploads/trip/covers/tokyo_lights.jpg",
                    stepsNumber = 0,
                    daysNumber = 0,
                    tagList = listOf(
                        NewTag("Citylife"),
                        NewTag("Asia")
                    )
                )
            ),
            mapPoints = emptyList()
        ),
        TestData(
            user = NewUserRequest("frank@example.com", "TestMe123", UserRole.User),
            profile = TestProfileRequest(
                username = "Frank Ocean",
                fullName = "Franklin Ocean",
                bio = "Living through music and mountains.",
                avatar = "/uploads/profile/avatars/frank_ocean.PNG",
                cover = "/uploads/profile/covers/frank_ocean.jpeg",
                followersNumber = 3,
                followingNumber = 4,
                tripsNumber = 1
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Peruvian Peaks",
                    description = "Trekking through the Andes and Machu Picchu.",
                    startDate = LocalDate.parse("2024-03-01"),
                    endDate = LocalDate.parse("2024-03-15"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Past,
                    coverPhoto = "/uploads/trip/covers/peruvian_peaks.jpg",
                    stepsNumber = 0,
                    daysNumber = 15,
                    tagList = listOf(
                        NewTag("Hiking"),
                        NewTag("South America")
                    )
                )
            ),
            mapPoints = emptyList()
        ),
        TestData(
            user = NewUserRequest("grace@example.com", "Welcome9A", UserRole.User),
            profile = TestProfileRequest(
                username = "Grace Hopper",
                fullName = "Grace M. Hopper",
                bio = "Discovering the world one line at a time.",
                avatar = "/uploads/profile/avatars/grace_hopper.jpg",
                cover = "/uploads/profile/covers/grace_hopper.jpg",
                followersNumber = 3,
                followingNumber = 4,
                tripsNumber = 2
            ),
            trips = listOf(
                TestTripRequest(
                    name = "Norwegian Fjords",
                    description = "Sailing and hiking among Norway’s fjords.",
                    startDate = LocalDate.parse("2024-09-01"),
                    endDate = LocalDate.parse("2024-09-12"),
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Past,
                    coverPhoto = "/uploads/trip/covers/norwegian_fjords.jpg",
                    stepsNumber = 0,
                    daysNumber = 12,
                    tagList = listOf(
                        NewTag("Sailing"),
                        NewTag("Nature")
                    )
                ),
                TestTripRequest(
                    name = "Sahara Stargazing",
                    description = "Camping under the stars in the desert.",
                    startDate = LocalDate.parse("2025-04-20"),
                    endDate = null,
                    type = TripVisibilityType.Public,
                    status = TripTimeStatus.Current,
                    coverPhoto = "/uploads/trip/covers/sahara_stargazing.jpg",
                    stepsNumber = 0,
                    daysNumber = 0,
                    tagList = listOf(
                        NewTag("Camping"),
                        NewTag("Stars")
                    )
                )
            ),
            mapPoints = emptyList()
        )
    )

    val commentsData = listOf(
        // mapPointId = 1 (Alpine Adventure - St. Anton am Arlberg)
        NewCommentRequest(
            senderProfileId = 2,
            mapPointId = 1,
            text = "One of the best starting points for hiking in the Alps. The air is so fresh!",
            sendDate = OffsetDateTime.parse("2024-07-15T14:10:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 4,
            mapPointId = 1,
            text = "I loved the vibe of this mountain village. Great food and cozy lodges.",
            sendDate = OffsetDateTime.parse("2024-07-15T15:45:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 6,
            mapPointId = 1,
            text = "It’s a paradise for ski lovers in winter, but summer hiking is just as amazing.",
            sendDate = OffsetDateTime.parse("2024-07-16T09:30:00+02:00")
        ),

        // mapPointId = 2 (Alpine Adventure - Zermatt)
        NewCommentRequest(
            senderProfileId = 1,
            mapPointId = 2,
            text = "Can’t believe how close the Matterhorn looks from here. Absolute must-visit!",
            sendDate = OffsetDateTime.parse("2024-07-20T18:00:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 3,
            mapPointId = 2,
            text = "Zermatt is such a peaceful place. No cars and stunning views everywhere.",
            sendDate = OffsetDateTime.parse("2024-07-21T11:15:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 5,
            mapPointId = 2,
            text = "Took the train up to Gornergrat—absolutely worth it for the views!",
            sendDate = OffsetDateTime.parse("2024-07-21T13:40:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 7,
            mapPointId = 2,
            text = "Definitely coming back in winter to see this place covered in snow.",
            sendDate = OffsetDateTime.parse("2024-07-22T10:00:00+02:00")
        ),

        // mapPointId = 3 (Weekend in Prague - Old Town Square)
        NewCommentRequest(
            senderProfileId = 4,
            mapPointId = 3,
            text = "Loved the street musicians and the Astronomical Clock. Such a vibe!",
            sendDate = OffsetDateTime.parse("2024-10-10T14:20:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 2,
            mapPointId = 3,
            text = "Grabbed coffee and watched the crowd go by—perfect afternoon.",
            sendDate = OffsetDateTime.parse("2024-10-10T15:00:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 6,
            mapPointId = 3,
            text = "So many cute cafés around here! Also lots of history to explore.",
            sendDate = OffsetDateTime.parse("2024-10-11T10:00:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 1,
            mapPointId = 3,
            text = "This square looks magical in the evening lights.",
            sendDate = OffsetDateTime.parse("2024-10-11T20:45:00+02:00")
        ),

        // mapPointId = 4 (Weekend in Prague - Charles Bridge)
        NewCommentRequest(
            senderProfileId = 3,
            mapPointId = 4,
            text = "Early morning walk on the bridge was unforgettable. No crowds, just peace.",
            sendDate = OffsetDateTime.parse("2024-10-11T07:30:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 5,
            mapPointId = 4,
            text = "Street artists and musicians everywhere—really brings the city to life.",
            sendDate = OffsetDateTime.parse("2024-10-11T12:15:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 7,
            mapPointId = 4,
            text = "Such a photogenic spot. Got some of my best trip photos here!",
            sendDate = OffsetDateTime.parse("2024-10-11T17:00:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 2,
            mapPointId = 4,
            text = "Crossing the bridge at sunset was a magical experience.",
            sendDate = OffsetDateTime.parse("2024-10-11T19:00:00+02:00")
        ),
        NewCommentRequest(
            senderProfileId = 1,
            mapPointId = 4,
            text = "Be careful of the crowds, but don’t miss this iconic spot!",
            sendDate = OffsetDateTime.parse("2024-10-11T20:00:00+02:00")
        )
    )

    val likesData = listOf(
        LikeRequest(profileId = 2, mapPointId = 1),
        LikeRequest(profileId = 3, mapPointId = 1),
        LikeRequest(profileId = 4, mapPointId = 1),
        LikeRequest(profileId = 5, mapPointId = 1),
        LikeRequest(profileId = 6, mapPointId = 1),
        LikeRequest(profileId = 7, mapPointId = 1),

        LikeRequest(profileId = 3, mapPointId = 2),
        LikeRequest(profileId = 4, mapPointId = 2),
        LikeRequest(profileId = 5, mapPointId = 2),

        LikeRequest(profileId = 1, mapPointId = 3),
        LikeRequest(profileId = 3, mapPointId = 3),
        LikeRequest(profileId = 4, mapPointId = 3),
        LikeRequest(profileId = 5, mapPointId = 3),
        LikeRequest(profileId = 7, mapPointId = 3),

        LikeRequest(profileId = 1, mapPointId = 4),
        LikeRequest(profileId = 2, mapPointId = 4),
        LikeRequest(profileId = 4, mapPointId = 4),
        LikeRequest(profileId = 5, mapPointId = 4),
        LikeRequest(profileId = 7, mapPointId = 4),
        LikeRequest(profileId = 6, mapPointId = 4)
    )

    val followsData = listOf(
        FollowUserRequest(followerId = 1, followingId = 2),
        FollowUserRequest(followerId = 1, followingId = 4),
        FollowUserRequest(followerId = 1, followingId = 5),
        FollowUserRequest(followerId = 1, followingId = 7),
        FollowUserRequest(followerId = 1, followingId = 6),

        FollowUserRequest(followerId = 2, followingId = 1),
        FollowUserRequest(followerId = 2, followingId = 3),
        FollowUserRequest(followerId = 2, followingId = 4),

        FollowUserRequest(followerId = 3, followingId = 7),
        FollowUserRequest(followerId = 3, followingId = 1),
        FollowUserRequest(followerId = 3, followingId = 4),
        FollowUserRequest(followerId = 3, followingId = 5),
        FollowUserRequest(followerId = 3, followingId = 6),

        FollowUserRequest(followerId = 4, followingId = 1),
        FollowUserRequest(followerId = 4, followingId = 5),
        FollowUserRequest(followerId = 4, followingId = 7),

        FollowUserRequest(followerId = 6, followingId = 1),
        FollowUserRequest(followerId = 6, followingId = 3),
        FollowUserRequest(followerId = 6, followingId = 4),
        FollowUserRequest(followerId = 6, followingId = 5),

        FollowUserRequest(followerId = 7, followingId = 1),
        FollowUserRequest(followerId = 7, followingId = 4),
        FollowUserRequest(followerId = 7, followingId = 6),
        FollowUserRequest(followerId = 7, followingId = 2),
    )
}