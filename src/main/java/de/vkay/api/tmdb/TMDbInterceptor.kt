package de.vkay.api.tmdb

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TMDbInterceptor(
    private val apiKey: String,
    private val bearerToken: String,
    private val modRequest: ((builder: Request.Builder) -> Unit)? = null
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request.url.newBuilder()
            .addQueryParameter("api_key", apiKey)

        val newRequestBuilder = request.newBuilder()
            .url(httpUrl.build())

        modRequest?.invoke(newRequestBuilder)

        if (bearerToken.isNotBlank()) {
            newRequestBuilder.addHeader("Authorization", "Bearer $bearerToken")
        }

        return chain.proceed(newRequestBuilder.build())
    }
}