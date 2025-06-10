package com.muhammetkonukcu.specifind.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.cash.paging.PagingSource
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuery(entity: HistoryEntity)

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun removeQueryFromHistory(id: Int)

    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getAllHistory(): PagingSource<Int, HistoryEntity>
}