package ru.mareanexx.travelogue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TravelogueBackendApplication

fun main(args: Array<String>) {
	runApplication<TravelogueBackendApplication>(*args)
}
