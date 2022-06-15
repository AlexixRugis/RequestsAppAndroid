package com.artech.requestsappandroid.di

import com.artech.requestsappandroid.data.remote.api.RequestsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun baseUrl() = "http://10.0.2.2:8000/api/v1/"

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }


        return OkHttpClient.Builder()
            .addInterceptor(interceptor) // same for .addInterceptor(...)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit() : RequestsApi =
        Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpClient())
            .build()
            .create(RequestsApi::class.java)
}