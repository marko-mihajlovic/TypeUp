package com.typeup

import com.typeup.search_apps.data.SearchAppsUseCase
import com.typeup.search_apps.data.model.SearchAppsUiState
import com.typeup.search_apps.data.repo.test.FakeAppsRepo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.isA
import org.junit.Test

class TestSearchAppsUseCase {

    @Test
    fun basic_test() = runBlocking {
        val searchAppsUseCase = SearchAppsUseCase(FakeAppsRepo(5))

        assertThat(
            searchAppsUseCase("name_of_app_that_does_not_exits").firstOrNull(),
            isA(SearchAppsUiState.Loading::class.java)
        )

        assertThat(
            searchAppsUseCase("name_of_app_that_does_not_exits").lastOrNull(),
            isA(SearchAppsUiState.Error::class.java)
        )

        assertThat(
            searchAppsUseCase("app").lastOrNull(),
            isA(SearchAppsUiState.Success::class.java)
        )
    }

    @Test
    fun test_that_order_stays_same_when_apps_are_randomly_shuffled() = runBlocking {
        repeat(100) {
            test()
        }
    }

    private suspend fun test() {
        val searchAppsUseCase = SearchAppsUseCase(FakeAppsRepo(5))

        val apps1 = searchAppsUseCase("bacon").lastOrNull()
        require(apps1 is SearchAppsUiState.Success)
        assertThat(
            apps1.list.map { x ->
                x.appName
            },
            contains(
                "Bacon", "Abc_bacon", "Salty_bacon",
            )
        )

        val apps2 = searchAppsUseCase("abc").lastOrNull()
        require(apps2 is SearchAppsUiState.Success)
        assertThat(
            apps2.list.map { x ->
                x.appName
            },
            contains(
                "Abc", "Abc2", "Abc_bacon", "Abc_johnny", "Last_abc",
            )
        )
    }
}