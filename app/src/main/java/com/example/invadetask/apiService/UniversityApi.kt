package com.example.invadetask.apiService

import com.example.invadetask.models.University
import retrofit2.http.GET

interface UniversityApi {
    @GET("search?country=United%20Arab%20Emirates")
    suspend fun getUniversities(): List<University>
}