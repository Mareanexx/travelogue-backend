package ru.mareanexx.travelogue.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.FileNotFoundException
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfig(
    @Value("\${firebase-key-path}") private val keyPath: String,
) {
    @PostConstruct
    fun init() {
        val serviceAccount = this::class.java.classLoader.getResourceAsStream("firebase/serviceAccountKey.json")
            ?: throw FileNotFoundException("Firebase service account file not found in classpath")
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }
}