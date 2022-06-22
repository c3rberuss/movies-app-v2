package xyz.c3rberuss.movies.requestmanager

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        val url: HttpUrl = request
            .url
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}