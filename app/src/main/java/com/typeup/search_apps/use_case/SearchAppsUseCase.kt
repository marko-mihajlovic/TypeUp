package com.typeup.home.use_case

import com.typeup.home.model.AppInfo
import com.typeup.home.model.SearchAppsUiState
import com.typeup.home.repo.InstalledAppsRepo
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
                x.appNameLowercase.contains(filterString)
            }
            .sortedWith(
                compareByDescending<AppInfo> { x ->
                    x.appNameLowercase.startsWith(filterString)
                }.thenBy { x ->
                    x.appNameLowercase.length
                }
            )
    }

    private fun capList(list: List<AppInfo>): List<AppInfo> {
        return list.subList(0, min(list.size, repo.getMaxSize()))
    }

}