package com.typeup

import com.typeup.home.HomeViewModel
import com.typeup.home.SearchAppsUiState
import com.typeup.home.repo.FakeAppsRepo
import com.typeup.home.use_case.SearchAppsUseCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Test

class TestHomeViewModel {

    @Test
    fun test_initial_state() {
        val viewModel = HomeViewModel(SearchAppsUseCase(FakeAppsRepo()))

        assertThat(
            viewModel.uiState.value,
            Matchers.isA(SearchAppsUiState.Loading::class.java)
        )
    }

}