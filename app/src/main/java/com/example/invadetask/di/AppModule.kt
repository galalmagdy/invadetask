package com.example.invadetask.di

import android.content.Context
import androidx.room.Room
import com.example.invadetask.apiService.UniversityApi
import com.example.invadetask.constans.Const.BASE_URL
import com.example.invadetask.room.UniversityDao
import com.example.invadetask.room.UniversityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUniversityDatabase(@ApplicationContext appContext: Context): UniversityDatabase {
        return Room.databaseBuilder(
            appContext,
            UniversityDatabase::class.java, "university_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUniversityDao(database: UniversityDatabase): UniversityDao {
        return database.universityDao()
    }

    @Singleton
    @Provides
    fun getRetroFitServiceInstance(retrofit: Retrofit): UniversityApi {
        return retrofit.create(UniversityApi::class.java)
    }

    @Singleton
    @Provides
    fun getRetroFitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext.applicationContext
    }

}