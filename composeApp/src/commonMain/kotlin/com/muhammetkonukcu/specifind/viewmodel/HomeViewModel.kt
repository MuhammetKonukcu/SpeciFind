package com.muhammetkonukcu.specifind.viewmodel

import androidx.lifecycle.ViewModel
import com.muhammetkonukcu.specifind.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

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
}