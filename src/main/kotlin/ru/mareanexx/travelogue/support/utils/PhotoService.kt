package ru.mareanexx.travelogue.support.utils

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.*

@Component
class PhotoService {
    fun saveFile(file: MultipartFile, uploadDir: String, pathMiddle: String): String {
        val dir = File(uploadDir)
        if (!dir.exists()) dir.mkdirs()

        val fileName = UUID.randomUUID().toString() + "-" + file.originalFilename
        val fullPath = Paths.get(uploadDir, fileName)
        file.transferTo(fullPath.toFile())

        return "/uploads/${pathMiddle}/$fileName"
    }

    fun deleteFileIfExists(relativePath: String) {
        try {
            val file = File(".$relativePath")
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            println("Не удалось удалить файл: $relativePath. Причина: ${e.message}")
        }
    }
}