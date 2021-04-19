package de.vkay.api.tmdb

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class TMDbInterceptor(
    private val apiKey: String,
    private val bearerToken: String,
    private val modRequest: ((builder: Request.Builder) -> Unit)? = null
) : Interceptor {

    private var accessToken: String? = null
    private var onlineCacheDurationSeconds = TimeUnit.HOURS.toSeconds(2)
    private var offlineCacheDurationSeconds = TimeUnit.DAYS.toSeconds(2)
    private var onlineCondition: (() -> Boolean)? = null

    fun setAccessToken(token: String?) {
        this.accessToken = token
    }

    fun onlineCondition(condition: (() -> Boolean)?) {
        onlineCondition = condition
    }

    fun onlineCache(duration: Long, unit: TimeUnit) = apply {
        onlineCacheDurationSeconds = unit.toSeconds(duration)
    }

    fun offlineCache(duration: Long, unit: TimeUnit) = apply {
        offlineCacheDurationSeconds = unit.toSeconds(duration)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request.url.newBuilder()
            .addQueryParameter("api_key", apiKey)

        val newRequestBuilder = request.newBuilder()
            .url(httpUrl.build())

        modRequest?.invoke(newRequestBuilder)

        // Cache
        onlineCondition?.let { cond ->
            if (cond.invoke())
                newRequestBuilder.header("Cache-Control",
                    "public, max-age=$onlineCacheDurationSeconds"
                )
            else
                newRequestBuilder.header("Cache-Control",
                    "public, only-if-cached, max-stale=$offlineCacheDurationSeconds"
                )
        }

        if (accessToken != null) {
            newRequestBuilder.addHeader("Authorization", "Bearer $accessToken")
        } else if (bearerToken.isNotBlank()) {
            newRequestBuilder.addHeader("Authorization", "Bearer $bearerToken")
        }

        return chain.proceed(newRequestBuilder.build())

        /*
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = if (InternetConnection.isConnected(context))
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

            return chain.proceed(request)
        }
         */
    }
}