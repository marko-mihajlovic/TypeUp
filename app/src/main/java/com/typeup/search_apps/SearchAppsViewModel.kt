package com.typeup.search_apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typeup.search_apps.data.SearchAppsUseCase
import com.typeup.search_apps.data.model.SearchAppsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAppsViewModel @Inject constructor(
    private val searchAppsUseCase: SearchAppsUseCase,
) : ViewModel() {

    private val _apps = MutableStateFlow<SearchAppsUiState>(SearchAppsUiState.Loading())
    val uiState: StateFlow<SearchAppsUiState> = _apps

    init {
        searchApps("")
    }

    fun searchApps(filterText: String, refresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            searchAppsUseCase(filterText, refresh).collect { x ->
                _apps.value = x
            }
        }
    }

}


