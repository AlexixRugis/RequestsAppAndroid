package com.artech.requestsappandroid.utils.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.StringBuilder
import java.util.*


class AddCookiesInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val sharedPreferences = context.getSharedPreferences(
            "com.artech.requestsappandroid", Context.MODE_PRIVATE
        )
        val csrf = sharedPreferences.getString(CSRF_COOKIE, String())
        val session = sharedPreferences.getString(SESSION_COOKIE, String())

        val cookie = buildString {
            if (csrf != null) {
                append("csrftoken=${csrf}; ")
                builder.addHeader("X-CSRFToken", csrf)
            }
            if (session != null) {
                append("sessionid=${session}; ")
            }
        }

        builder.addHeader("Cookie", cookie)

        return chain.proceed(builder.build())
    }

    companion object {
        const val CSRF_COOKIE = "CSRF"
        const val SESSION_COOKIE = "SESSION"
    }
}