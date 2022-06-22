package xyz.c3rberuss.movies.requestmanager.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.c3rberuss.movies.requestmanager.ApiKeyInterceptor
import xyz.c3rberuss.movies.requestmanager.TheMovieDbApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun providesTheMovieDbApi(): TheMovieDbApi {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient().newBuilder().apply {
            callTimeout(90, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            addInterceptor(ApiKeyInterceptor(TheMovieDbApi.API_KEY))
        }.build()

        val moshi: Moshi = Moshi.Builder().apply {
            add(KotlinJsonAdapterFactory())
        }.build()

        return Retrofit.Builder()
            .baseUrl(TheMovieDbApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
            .build().run {
                create(TheMovieDbApi::class.java)
            }
    }
}
