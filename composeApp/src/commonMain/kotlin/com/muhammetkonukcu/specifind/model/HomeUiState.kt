package com.muhammetkonukcu.specifind.model

data class HomeUiState(
    val keyword: String = "",
    val sites: String = "",
    val fileType: String = "",
    val safeSearchEnabled: Boolean = true,
    val showMoreOptions: Boolean = false,
    val excludedSites: String = "",
    val language: String = "",
    val searchCategory: Pair<String, String> = Pair("", "")
)