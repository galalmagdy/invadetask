package com.example.invadetask.viewModels

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invadetask.models.University
import com.example.invadetask.repo.UniversitiesRepo
import com.example.invadetask.room.UniversityDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")

class UniversityViewModel @Inject constructor(
    private val repository: UniversitiesRepo,
    private val universityDao: UniversityDao,
    private val context: Context
) : ViewModel() {

    private val _universities = mutableStateOf<List<University>>(emptyList())
    val universities: State<List<University>> = _universities

    init {
        if (isConnected()) {
            fetchDataFromApiAndInsertToDb()
        } else {
            fetchDataFromDataBase()
        }
    }
     fun fetchDataFromApiAndInsertToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _universities.value = repository.getAllUniversities()
                universityDao.insertAll(_universities.value.map { university ->
                    University(
                        name = university.name,
                        stateProvince = university.stateProvince,
                        domains = university.domains,
                        webPages = university.webPages,
                        country = university.country,
                        alphaTwoCode = university.alphaTwoCode
                    )
                })
            } catch (e: Exception) {
                Log.e("Error","fetching data from API")
            }
        }
    }
    private fun fetchDataFromDataBase() {
        viewModelScope.launch {
            try {
            _universities.value = repository.getAllUniversitiesFromDataBase()
            } catch (e: Exception) {
                Log.e("Error","Error fetching data from database")

            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}