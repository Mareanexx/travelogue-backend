package ru.mareanexx.travelogue.support.firebase.type

enum class MessageType(val title: String) {
    NewComment("New comment"),
    NewLike("New like"),
    NewTrip("New trip"),
    NewFollow("You have a new follower"),
    NewMapPoint("New map point")
}