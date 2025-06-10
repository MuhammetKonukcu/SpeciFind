package com.muhammetkonukcu.specifind.room.repository

import app.cash.paging.PagingData
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryLocalRepository {
    suspend fun insertQuery(entity: HistoryEntity)
    suspend fun removeQueryFromHistory(id: Int)
    fun getAllHistory(pageSize: Int = 20): Flow<PagingData<HistoryEntity>>
}