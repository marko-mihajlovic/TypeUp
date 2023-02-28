package com.typeup.home.model

sealed class SearchAppsUiState {
    data class Success(val list: List<AppInfo>) : SearchAppsUiState()
    data class Error(val msg: String) : SearchAppsUiState()
    data class Loading(val msg: String = "") : SearchAppsUiState()
}