package com.typeup.search_apps.data

import com.typeup.search_apps.data.model.AppInfo
import com.typeup.search_apps.data.model.SearchAppsUiState
import com.typeup.search_apps.data.repo.InstalledAppsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.min

class SearchAppsUseCase @Inject constructor(
    private val repo: InstalledAppsRepo,
) {

    suspend operator fun invoke(
        filterText: String,
        refresh: Boolean = false
    ): Flow<SearchAppsUiState> {
        return flow {
            repo.get(refresh).collect { x ->
                emit(getUiState(x, filterText))
            }
        }
    }

    private fun getUiState(list: List<AppInfo>, filterString: String): SearchAppsUiState {
        if (filterString.isEmpty())
            return SearchAppsUiState.Success(emptyList())

        if (list.isEmpty())
            return SearchAppsUiState.Loading()

        var filteredList = filterAndSortApps(list, filterString)
        filteredList = capList(filteredList)

        if (filteredList.isEmpty())
            return SearchAppsUiState.Error("No results found.")

        return SearchAppsUiState.Success(filteredList)
    }

    private fun filterAndSortApps(list: List<AppInfo>, filterString: String): List<AppInfo> {
        return list
            .filter { x ->
                x.appName.contains(filterString, true)
            }
            .sortedWith(
                compareByDescending<AppInfo> { x ->
                    x.appName.startsWith(filterString)
                }.thenBy { x ->
                    x.appName.length
                }
            )
    }

    private fun capList(list: List<AppInfo>): List<AppInfo> {
        return list.subList(0, min(list.size, repo.getMaxSize()))
    }

}