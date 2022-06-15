package com.artech.requestsappandroid.utils.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*


class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val sharedPreferences = context.getSharedPreferences(
                "com.artech.requestsappandroid", Context.MODE_PRIVATE
            )
            val memes = sharedPreferences.edit()

            for (header in originalResponse.headers("Set-Cookie")) {
                if (header.startsWith("csrftoken")) {
                    val parsed = parseCookie(header)
                    memes.putString("CSRF", if (parsed.equals("\"\"")) null else parsed)
                }
                else if (header.startsWith("sessionid")) {
                    val parsed = parseCookie(header)
                    memes.putString("SESSION", if (parsed.equals("\"\"")) null else parsed)
                }
            }

            memes.apply()
        }
        return originalResponse
    }


}