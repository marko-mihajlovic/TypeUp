package com.typeup.home

import com.typeup.model.AppInfo

sealed class SearchAppsUiState {
    data class Success(val list: List<AppInfo>) : SearchAppsUiState()
    data class Error(val msg: String) : SearchAppsUiState()
    data class Loading(val msg: String = "Loading...") : SearchAppsUiState()
}