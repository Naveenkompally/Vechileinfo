package com.jtpl.vechileinfo.mvvm.model

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestLog = StringBuilder("Sending request: ${request.url}")
        request.headers.forEach {
            requestLog.append("\n${it.first}: ${it.second}")
        }
        if (request.body != null) {
            val buffer = okio.Buffer()
            request.body!!.writeTo(buffer)
            requestLog.append("\nRequest Body: ${buffer.readUtf8()}")
        }
        println(requestLog.toString())

        val response = chain.proceed(request)

        val responseLog = StringBuilder("Received response for: ${response.request.url}")
        response.headers.forEach {
            responseLog.append("\n${it.first}: ${it.second}")
        }
        val responseBody = response.peekBody(Long.MAX_VALUE).string()
        responseLog.append("\nResponse Body: $responseBody")
        println(responseLog.toString())

        return response
    }
}
