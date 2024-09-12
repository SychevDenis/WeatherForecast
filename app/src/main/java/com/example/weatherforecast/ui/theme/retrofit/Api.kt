package com.example.weatherforecast.ui.theme.retrofit

import com.example.weatherforecast.ui.theme.retrofit.pojo.PojoData
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("current")
    suspend fun findWeatherCity( // функция поиска города
        @Query("access_key") key: String,
        @Query("query") city: String
    ): PojoData
}