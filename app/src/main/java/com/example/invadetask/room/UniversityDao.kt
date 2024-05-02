package com.example.invadetask.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.invadetask.models.University

@Dao
interface UniversityDao {
    @Query("SELECT * FROM universities")
    fun getAll(): List<University>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(universities: List<University>)
}