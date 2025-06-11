package com.muhammetkonukcu.specifind.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muhammetkonukcu.specifind.room.Converter
import com.muhammetkonukcu.specifind.room.dao.HistoryDao
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}