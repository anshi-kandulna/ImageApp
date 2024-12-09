package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService{
    @Headers("Authorization: U9Qc4mbGmV0hCLgTw63tBYeaLJcDCrWZF4CSzUmFx97PE3R7vw3lb5XX")
    @GET("curated")
    fun getImageData(
        @Query("per_page") perpage : Int = 80
    ) : Call<PexelsPhotoResponse>
}