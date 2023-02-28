package com.typeup.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typeup.home.use_case.SearchAppsUseCase
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

    public fun searchApps(filterText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchAppsUseCase(filterText).collect { x ->
                _apps.value = x
            }
        }
    }

}


