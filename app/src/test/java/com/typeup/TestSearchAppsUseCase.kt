package com.typeup

import com.typeup.home.model.SearchAppsUiState
import com.typeup.home.repo.FakeAppsRepo
import com.typeup.home.use_case.SearchAppsUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.isA
import org.junit.Test

class TestSearchAppsUseCase {

    @Test
    fun test() = runBlocking {
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

        val apps1 = searchAppsUseCase("bacon").lastOrNull()
        require(apps1 is SearchAppsUiState.Success)
        assertThat(
            apps1.list.map { x ->
                x.appName
            },
            contains(
                "bacon", "abc_bacon", "salty_bacon",
            )
        )

        val apps2 = searchAppsUseCase("abc").lastOrNull()
        require(apps2 is SearchAppsUiState.Success)
        assertThat(
            apps2.list.map { x ->
                x.appName
            },
            contains(
                "abc", "abc2", "abc_bacon", "abc_johnny", "last_abc",
            )
        )
    }
}