package com.ttw.pagingsample.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface MovieApi {
    @GET("discover/movie?sort_by=vote_count.desc")
    suspend fun getNowPlaying(@Query("page") page: Int,@Query("per_page")perPage:Int): Response<MovieResponse>


    companion object {
        const val API_KEY = "11d4fbee2af9f6b42f5fcec50cc2e357"
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MovieApi {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(ChuckInterceptor(networkConnectionInterceptor.applicationContext))
                .addInterceptor Interceptor@{ chain ->
                    val url = chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()

                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                    return@Interceptor chain.proceed(request)
                }
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)

        }
    }
}