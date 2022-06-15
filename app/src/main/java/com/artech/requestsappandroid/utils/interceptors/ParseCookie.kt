package com.artech.requestsappandroid.utils.interceptors

fun parseCookie(fullHeader: String): String? {
    val matcher = "=(.+?);".toRegex()
    return matcher.find(fullHeader)?.groupValues?.get(1)
}