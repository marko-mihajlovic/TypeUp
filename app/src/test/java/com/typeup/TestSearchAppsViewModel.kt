package com.typeup

import com.typeup.rules.MainDispatcherRule
import com.typeup.search_apps.SearchAppsViewModel
import com.typeup.search_apps.data.SearchAppsUseCase
import com.typeup.search_apps.data.repo.test.FakeAppsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TestSearchAppsViewModel {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test_initial_state() = runTest {
        val viewModel = SearchAppsViewModel(SearchAppsUseCase(FakeAppsRepo()))

        assertThat(
            viewModel.uiState.value.isLoading,
            `is`(true)
        )
    }

}