package com.artech.requestsappandroid.di

import android.content.Context
import com.artech.requestsappandroid.data.remote.api.RequestsApi
import com.artech.requestsappandroid.utils.interceptors.AddCookiesInterceptor
import com.artech.requestsappandroid.utils.interceptors.ReceivedCookiesInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// DI модуль

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun baseUrl() = "http://alexixrugis.pythonanywhere.com/api/v1/"

    @Provides
    fun provideHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        builder.addInterceptor(AddCookiesInterceptor(appContext))
        builder.addInterceptor(ReceivedCookiesInterceptor(appContext))


        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext appContext: Context) : RequestsApi =
        Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpClient(appContext))
            .build()
            .create(RequestsApi::class.java)
}