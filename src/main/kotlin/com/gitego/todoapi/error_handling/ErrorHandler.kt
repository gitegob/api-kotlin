package com.gitego.todoapi.error_handling

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.error.ErrorAttributeOptions.Include
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest

@RestController
class ErrorHandler(
    private val errorAttributes: ErrorAttributes? = null
) : ErrorController {

    @Suppress("UNCHECKED_CAST")
    @RequestMapping("/error")
    fun handleError(webRequest: WebRequest?): ResponseEntity<GenericError> {
        val attributes = this.errorAttributes!!.getErrorAttributes(
            webRequest,
            ErrorAttributeOptions.of(Include.MESSAGE, Include.BINDING_ERRORS)
        )
        val message = attributes["message"] as String?
        val path = attributes["path"] as String?
        val status = attributes["status"] as Int
        val error = GenericError(status = status, error = message, path = path)
        if (attributes.containsKey("errors")) {
            val fieldErrors = attributes["errors"] as List<FieldError>?
            val validationErrors: MutableMap<String, String?> = HashMap()
            for (fieldError in fieldErrors!!) {
                validationErrors[fieldError.field] = fieldError.defaultMessage
            }
            error.validationErrors = validationErrors as MutableMap<String, String>?
        }
        return ResponseEntity.status(error.status).body(error)
    }
}
