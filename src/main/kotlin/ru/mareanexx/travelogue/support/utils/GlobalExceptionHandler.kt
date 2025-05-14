package ru.mareanexx.travelogue.support.utils

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.mareanexx.travelogue.api.response.WrappedResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<WrappedResponse<Nothing>> {

        val errors = ex.bindingResult.allErrors
            .joinToString { it.defaultMessage ?: "Invalid field" }

        return ResponseEntity.badRequest().body(
            WrappedResponse(message = "Validation failed: $errors", data = null)
        )
    }
}