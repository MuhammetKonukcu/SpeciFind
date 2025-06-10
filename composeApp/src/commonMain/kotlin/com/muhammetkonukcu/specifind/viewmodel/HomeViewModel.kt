package com.muhammetkonukcu.specifind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammetkonukcu.specifind.model.HomeUiState
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity
import com.muhammetkonukcu.specifind.room.repository.HistoryLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(val localRepository: HistoryLocalRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onKeywordChange(new: String) {
        _uiState.update { it.copy(keyword = new) }
    }

    fun onSitesChange(new: String) {
        _uiState.update { it.copy(sites = new) }
    }

    fun onFileTypeChange(new: String) {
        _uiState.update { it.copy(fileType = new) }
    }

    fun onSafeSearchToggle(enabled: Boolean) {
        _uiState.update { it.copy(safeSearchEnabled = enabled) }
    }

    fun onShowMoreOptionsToggle() {
        _uiState.update { it.copy(showMoreOptions = !it.showMoreOptions) }
    }

    fun onExcludedSitesChange(new: String) {
        _uiState.update { it.copy(excludedSites = new) }
    }

    fun onLanguageChange(new: String) {
        _uiState.update { it.copy(language = new) }
    }

    fun onSearchCategoryChange(new: String) {
        _uiState.update { it.copy(searchCategory = new) }
    }

    fun buildQuery(): String {
        val state = _uiState.value
        val parts = mutableListOf<String>()

        // 1) Required: keyword (must not be blank)
        state.keyword.takeIf { it.isNotBlank() }?.let {
            parts += it.trim()
        } ?: run {
            return ""
        }

        // 2) site:
        state.sites.takeIf { it.isNotBlank() }?.let {
            it.split(',').map(String::trim).filter(String::isNotEmpty).forEach { site ->
                parts += "site:${site.lowercase()}"
            }
        }

        // 3) exclude sites (using -site:)
        state.excludedSites.takeIf { it.isNotBlank() }?.let {
            it.split(',').map(String::trim).filter(String::isNotEmpty).forEach { site ->
                parts += "-site:${site.lowercase()}"
            }
        }

        // 4) fileType
        state.fileType.takeIf { it.isNotBlank() }?.let {
            parts += "filetype:${it.lowercase()}"
        }

        // 5) language
        state.language.takeIf { it.isNotBlank() }?.let {
            parts += "lang:${it}"
        }

        // 6) category (searchType)
        state.searchCategory.takeIf { it.isNotBlank() }?.let {
            //It doesn't work on search engines. TODO(revisit later)
            //parts += "searchType=${it.lowercase()}"
        }

        // 7) safe search
        if (state.safeSearchEnabled) {
            //It doesn't work on search engines. TODO(revisit later)
            //parts += "safe=active"
        }

        saveQueryInHistory(
            entity = HistoryEntity(
                id = 0,
                keyword = state.keyword,
                safeSearch = state.safeSearchEnabled,
                site = state.sites.lowercase(),
                language = state.language.lowercase(),
                fileType = state.fileType.lowercase(),
                excludeSite = state.excludedSites.lowercase(),
                searchCategory = state.searchCategory.lowercase(),
            )
        )

        return parts.joinToString(separator = " ")
    }

    private fun saveQueryInHistory(entity: HistoryEntity) {
        viewModelScope.launch {
            localRepository.insertQuery(entity)
        }
    }

    fun clearUiState() {
        _uiState.value = HomeUiState()
    }
}