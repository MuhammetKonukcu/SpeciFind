package com.muhammetkonukcu.specifind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import app.cash.paging.map
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity
import com.muhammetkonukcu.specifind.room.repository.HistoryLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HistoryViewModel(private val localRepo: HistoryLocalRepository) : ViewModel() {
    val historyPagingDataFlow: Flow<PagingData<HistoryEntity>> =
        localRepo.getAllHistory()
            .cachedIn(viewModelScope)
            .map { data ->
                data.map {
                    it.copy(
                        language = it.language?.uppercase(),
                        fileType = it.fileType?.replaceFirstChar { it.titlecase() }
                    )
                }
            }

    fun removeQueryFromHistory(id: Int) {
        viewModelScope.launch {
            localRepo.removeQueryFromHistory(id)
        }
    }

    fun buildQuery(entity: HistoryEntity): String {
        val parts = mutableListOf<String>()

        // 1) Required: keyword (must not be blank)
        entity.keyword.takeIf { it.isNotBlank() }?.let {
            parts += it.trim()
        } ?: run {
            return ""
        }

        // 2) site:
        entity.site.takeIf { !it.isNullOrBlank() }?.let {
            it.split(',').map(String::trim).filter(String::isNotEmpty).forEach { site ->
                parts += "site:${site.lowercase()}"
            }
        }

        // 3) exclude sites (using -site:)
        entity.excludeSite.takeIf { !it.isNullOrBlank() }?.let {
            it.split(',').map(String::trim).filter(String::isNotEmpty).forEach { site ->
                parts += "-site:${site.lowercase()}"
            }
        }

        // 4) fileType
        entity.fileType.takeIf { !it.isNullOrBlank() }?.let {
            parts += "filetype:${it.lowercase()}"
        }

        // 5) language
        entity.language.takeIf { !it.isNullOrBlank() }?.let {
            parts += "lang:${it}"
        }

        return parts.joinToString(separator = " ")
    }
}