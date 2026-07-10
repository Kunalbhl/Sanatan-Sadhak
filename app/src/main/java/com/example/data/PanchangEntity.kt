package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "panchang_cache")
data class PanchangEntity(
    @PrimaryKey val date: String, // YYYY-MM-DD
    val tithi: String,
    val paksha: String,
    val nakshatra: String,
    val yoga: String,
    val karana: String,
    val sunrise: String,
    val sunset: String,
    val rahuKaal: String,
    val abhijeet: String,
    val festival: String,
    val details: String
)
