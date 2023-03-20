package com.typeup.search_apps.data.model

data class SearchAppsUiState(
    val data: List<AppInfo>,
    val isLoading: Boolean,
    val isError: Boolean = false,
) {
    fun errorMsg(): String = if (isError) "No results found." else ""
}

data class AppsRepoState(
    val data: List<AppInfo>,
    val isLoading: Boolean = false,
)
