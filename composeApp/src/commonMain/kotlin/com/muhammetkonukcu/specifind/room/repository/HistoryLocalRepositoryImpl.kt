package com.muhammetkonukcu.specifind.room.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import com.muhammetkonukcu.specifind.room.dao.HistoryDao
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

class HistoryLocalRepositoryImpl(private val historyDao: HistoryDao) : HistoryLocalRepository {
    override suspend fun insertQuery(entity: HistoryEntity) = historyDao.insertQuery(entity)

    override suspend fun removeQueryFromHistory(id: Int) = historyDao.removeQueryFromHistory(id)

    override fun getAllHistory(pageSize: Int): Flow<PagingData<HistoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { historyDao.getAllHistory() }
        ).flow
    }
}