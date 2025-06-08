package ru.mareanexx.travelogue.support.utils.test

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


@Component
@Profile("prod")
class TestDataLoader(
    private val testDataService: TestDataService
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Running TestDataLoader...")
        testDataService.insertTestData()
    }
}