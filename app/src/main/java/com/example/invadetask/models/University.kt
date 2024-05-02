package com.example.invadetask.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.invadetask.room.Converters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "universities")
@TypeConverters(Converters::class)
data class University(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    @SerializedName("state-province" )
    val stateProvince: String?,

    val domains: List<String>?,

    @SerializedName("web_pages")
    val webPages:List<String>?,

    val country: String?,
    @SerializedName("alpha_two_code")
    val alphaTwoCode: String?
)