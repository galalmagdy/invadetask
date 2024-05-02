package com.example.invadetask.repo

import com.example.invadetask.apiService.UniversityApi
import com.example.invadetask.models.University
import com.example.invadetask.room.UniversityDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UniversitiesRepo @Inject constructor(private val api: UniversityApi,private val universityDao: UniversityDao) {
    suspend fun getAllUniversities(): List<University>{
        return api.getUniversities()
    }
    suspend fun getAllUniversitiesFromDataBase(): List<University> {
        return withContext(Dispatchers.IO) {
            universityDao.getAll()
        }
    }
}