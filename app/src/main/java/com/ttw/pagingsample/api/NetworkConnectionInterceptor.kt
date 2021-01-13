package com.ttw.pagingsample.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkConnectionInterceptor(context: Context) : Interceptor {
   var applicationContext = context.applicationContext!!

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make Sure You have an active network connection")
        try {
            val request = chain.request()
            if (isInternetAvailable()) request.newBuilder()
                .header("Cache-Control", "public, max-age=" + 5).build()
            else request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
            return chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            throw IOException()
        }

    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val conManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        conManager?.let {
            it.getNetworkCapabilities(conManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }


}