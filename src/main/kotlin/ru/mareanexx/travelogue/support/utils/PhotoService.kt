package ru.mareanexx.travelogue.support.utils

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.*

@Component
class PhotoService {
    fun saveFile(file: MultipartFile, uploadDir: String, pathMiddle: String): String {
        val rootPath = Paths.get("").toAbsolutePath().toString()
        val fullUploadDir = Paths.get(rootPath, uploadDir).toString()

        val dir = File(fullUploadDir)
        if (!dir.exists()) dir.mkdirs()

        val fileName = UUID.randomUUID().toString() + "-" + file.originalFilename
        val fullPath = Paths.get(fullUploadDir, fileName)
        file.transferTo(fullPath.toFile())

        return "/uploads/${pathMiddle}/$fileName"
    }

    fun deleteFileIfExists(relativePath: String) {
        try {
            val rootPath = Paths.get("").toAbsolutePath().toString()
            val fullPath = Paths.get(rootPath, relativePath).toString()
            val file = File(fullPath)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            println("Не удалось удалить файл: $relativePath. Причина: ${e.message}")
        }
    }
}