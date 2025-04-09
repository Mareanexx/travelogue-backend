package ru.mareanexx.travelogue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@EnableJdbcRepositories
@SpringBootApplication
class TravelogueBackendApplication

fun main(args: Array<String>) {
	runApplication<TravelogueBackendApplication>(*args)
}