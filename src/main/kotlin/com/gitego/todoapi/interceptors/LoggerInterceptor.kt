package com.gitego.todoapi.interceptors

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.lang.Nullable
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class LoggerInterceptor:HandlerInterceptor {

    private val log: Logger = LoggerFactory.getLogger(LoggerInterceptor::class.java)

    @Throws(Exception::class)
   override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        @Nullable modelAndView: ModelAndView?
    ) {
        log.info("USER HAS SUCCESSFULLY MADE A REQUEST TO ${request.requestURI}")
        // super.postHandle(request, response, handler,
        // modelAndView);
    }

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
       log.info("USER IS ATTEMPTING TO MAKE A REQUEST TO ${request.requestURI}")
        return true
    }
}