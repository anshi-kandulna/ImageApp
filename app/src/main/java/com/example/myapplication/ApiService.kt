package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    // Add Authorization header with API Key
    @Headers("Authorization: U9Qc4mbGmV0hCLgTw63tBYeaLJcDCrWZF4CSzUmFx97PE3R7vw3lb5XX")
    @GET("curated")
    fun getRandomImages(
        @Query("per_page") perPage: Int,
        @Query("page") pageCount: Int
    ): Call<PexelsPhotoResponse>

    @Headers("Authorization: U9Qc4mbGmV0hCLgTw63tBYeaLJcDCrWZF4CSzUmFx97PE3R7vw3lb5XX")
    @GET("search")
    fun searchImages(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") pageCount: Int
    ): Call<PexelsPhotoResponse>
}

