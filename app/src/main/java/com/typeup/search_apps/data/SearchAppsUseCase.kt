package com.typeup.search_apps.data

import com.typeup.search_apps.data.model.AppInfo
import com.typeup.search_apps.data.model.AppsRepoState
import com.typeup.search_apps.data.model.SearchAppsUiState
import com.typeup.search_apps.data.repo.InstalledAppsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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

    private fun getUiState(repoState: AppsRepoState, filterString: String): SearchAppsUiState {
        if (filterString.isEmpty())
            return SearchAppsUiState(emptyList(), isLoading = repoState.isLoading)

        if (repoState.data.isEmpty())
            return SearchAppsUiState(repoState.data, isLoading = repoState.isLoading)

        val modifiedList = modifyList(repoState.data, filterString)

        if (modifiedList.isEmpty())
            return SearchAppsUiState(
                data = modifiedList,
                isLoading = repoState.isLoading,
                isError = true,
            )

        return SearchAppsUiState(
            data = modifiedList,
            isLoading = repoState.isLoading,
        )
    }

    private fun modifyList(list: List<AppInfo>, filterString: String): List<AppInfo> {
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
            .take(repo.getMaxSize())
    }

}