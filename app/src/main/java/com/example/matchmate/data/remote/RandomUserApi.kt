package com.example.matchmate.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 10,
        @Query("seed") seed: String = "matchmate"
    ): RandomUserResponse
}