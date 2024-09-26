package com.example.test.network

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException

class CustomInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Log request
        println("Request:")
        println("URL: ${request.url}")
        println("Method: ${request.method}")
        println("Headers: ${request.headers}")
        logRequestBody(request.body)

        // Proceed with the request
        val response = chain.proceed(request)

        // Log response
        println("\nResponse:")
        println("Code: ${response.code}")
        println("Message: ${response.message}")
        println("Headers: ${response.headers}")
        logResponseBody(response.body)

        return response
    }

    private fun logRequestBody(requestBody: RequestBody?) {
        requestBody ?: return
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        val bodyString = buffer.readUtf8()
        println("Body: $bodyString")
    }

    private fun logResponseBody(responseBody: ResponseBody?) {
        responseBody ?: return
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer
        val bodyString = buffer.clone().readUtf8()
        println("Body: $bodyString")
    }
}