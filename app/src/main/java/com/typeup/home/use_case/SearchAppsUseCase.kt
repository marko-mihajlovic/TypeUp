package com.typeup.home.use_case

import com.typeup.home.SearchAppsUiState
import com.typeup.home.repo.InstalledAppsRepo
import com.typeup.model.AppInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAppsUseCase @Inject constructor(
    private val repo: InstalledAppsRepo,
) {

    suspend operator fun invoke(filterText: String): Flow<SearchAppsUiState> {
        return flow {
            repo.get().collect() { x ->
                emit(getUiState(x, filterText))
            }
        }
    }

    private fun getUiState(list: List<AppInfo>, filterString: String): SearchAppsUiState {
        if(filterString.isEmpty())
            return SearchAppsUiState.Success(emptyList())

        if (list.isEmpty())
            return SearchAppsUiState.Loading()

        val filteredList = list.filter { x ->
            x.appNameLowercase.contains(filterString)
        }.sortedWith(
            compareByDescending<AppInfo>{ x ->
                x.appNameLowercase.startsWith(filterString)
            }.thenBy{ x ->
                x.appNameLowercase.length
            }
        )

        if (filteredList.isEmpty())
            return SearchAppsUiState.Error("No results found")

        return SearchAppsUiState.Success(filteredList)
    }

}