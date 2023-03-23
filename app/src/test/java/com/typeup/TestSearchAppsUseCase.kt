package com.typeup

import com.typeup.search_apps.data.SearchAppsUseCase
import com.typeup.search_apps.data.model.AppInfo
import com.typeup.search_apps.data.repo.test.FakeAppsRepo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test

class TestSearchAppsUseCase {

    @Test
    fun basic_test() = runBlocking {
        val searchAppsUseCase = SearchAppsUseCase(FakeAppsRepo(5))

        assertThat(
            searchAppsUseCase("name_of_app_that_does_not_exits").firstOrNull()?.isLoading,
            `is`(true)
        )

        assertThat(
            searchAppsUseCase("name_of_app_that_does_not_exits").lastOrNull()?.isError,
            `is`(true)
        )

        assertThat(
            searchAppsUseCase("app").lastOrNull()?.data,
            `is`(not(empty()))
        )
        assertThat(
            searchAppsUseCase("app").lastOrNull()?.isError,
            `is`(false)
        )
        assertThat(
            searchAppsUseCase("app").lastOrNull()?.isLoading,
            `is`(false)
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

        val apps1 = searchAppsUseCase("bacon").lastOrNull()?.data
        require(apps1 is List<AppInfo>)
        assertThat(
            apps1.map { x ->
                x.appName
            },
            contains(
                "Bacon", "Abc_bacon", "Salty_bacon",
            )
        )

        val apps2 = searchAppsUseCase("abc").lastOrNull()?.data
        require(apps2 is List<AppInfo>)
        assertThat(
            apps2.map { x ->
                x.appName
            },
            contains(
                "Abc", "Abc2", "Abc_bacon", "Abc_johnny", "Last_abc",
            )
        )
    }
}