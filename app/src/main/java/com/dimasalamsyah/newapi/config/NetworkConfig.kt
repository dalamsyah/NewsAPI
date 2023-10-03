package com.dimasalamsyah.newapi.config

import com.dimasalamsyah.newapi.model.SearchResponse
import com.dimasalamsyah.newapi.model.SourceResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkConfig {
    // set interceptor
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return  okHttpClient
    }
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // https://newsapi.org/v2/top-headlines/sources?country=id&apiKey=d08fcc7120cd4c018ec2dd2c360dead6&category=technology
    }
    fun getService() = getRetrofit().create(News::class.java)
}
interface News {
    @GET("top-headlines/sources?apiKey=d08fcc7120cd4c018ec2dd2c360dead6")
    fun getSources(@Query("category") category: String?): Call<SourceResponse>

    @GET("everything?apiKey=d08fcc7120cd4c018ec2dd2c360dead6")
    fun getSearch(@Query("q") q: String?): Call<SearchResponse>

}