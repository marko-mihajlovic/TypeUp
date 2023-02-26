package com.typeup

import com.typeup.home.HomeViewModel
import com.typeup.home.SearchAppsUiState
import com.typeup.home.repo.FakeAppsRepo
import com.typeup.home.use_case.SearchAppsUseCase
import com.typeup.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TestHomeViewModel {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test_initial_state() = runTest {
        val viewModel = HomeViewModel(SearchAppsUseCase(FakeAppsRepo()))

        assertThat(
            viewModel.uiState.value,
            Matchers.isA(SearchAppsUiState.Success::class.java)
        )
    }

}