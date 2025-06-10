package com.muhammetkonukcu.specifind.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammetkonukcu.specifind.room.dao.HistoryDao
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}