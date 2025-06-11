package com.muhammetkonukcu.specifind.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val keyword: String,
    val safeSearch: Boolean,
    val site: String? = null,
    val language: String? = null,
    val fileType: String? = null,
    val excludeSite: String? = null,
    val searchCategory: Pair<String, String> = "" to ""
)
