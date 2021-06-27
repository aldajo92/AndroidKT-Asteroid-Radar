package com.udacity.asteroidradar.api.network

import com.udacity.asteroidradar.models.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsJson(
        @Query("start_date") start_date: String,
        @Query("api_key") api_key: String
    ): String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") api_key: String): PictureOfDay

}