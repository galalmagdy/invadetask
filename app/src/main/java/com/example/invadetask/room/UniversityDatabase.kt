package com.example.invadetask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.invadetask.models.University

@Database(entities = [University::class], version = 1)
abstract class UniversityDatabase : RoomDatabase() {
    abstract fun universityDao(): UniversityDao
}