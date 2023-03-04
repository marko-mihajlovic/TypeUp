package com.typeup.search_apps

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.util.CustomListViewMatcher.withListSize
import com.typeup.util.SharedPref
import org.hamcrest.Matchers.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSearchActivity {

    private fun searchInput() = withId(R.id.searchInput)

    private fun onAcceptBtn() = onView(withText("Accept"))
    private fun onSearchInput() = onView(searchInput())
    private fun onListView() = onView(withId(R.id.listView))
    private fun onMsgTxt() = onView(withId(R.id.msgTxt))

    @Test
    fun test_activity() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        SharedPref.edit(context).clear().commit()

        ActivityScenario.launch(SearchActivity::class.java).use {

            onAcceptBtn() .perform(click())

            onListView().check(isCompletelyBelow(searchInput()))

            onSearchInput().perform(typeText("gm")) // gmail
            onListView().check(matches(withListSize(1)))

            onSearchInput().perform(replaceText("non_existent_app"))
            onListView().check(matches(withListSize(0)))
            onMsgTxt().check(matches(withText("No results found.")))
            onMsgTxt().check(isCompletelyBelow(searchInput()))

            onSearchInput().perform(clearText())

            onSearchInput().perform(typeText("a"))
            onListView().check(matches(withListSize(MaxShownItems.default)))
        }

        ActivityScenario.launch(SearchActivity::class.java).use {
            Intents.init()

            onSearchInput().perform(typeText("o"))
            onListView().check(matches(withListSize(MaxShownItems.default)))

            onData(anything()).atPosition(0).perform(click())
            val intents = Intents.getIntents()
            assertThat(intents.size, `is`(1))
            assertTrue(intents[0].action.equals(Intent.ACTION_MAIN))
            assertTrue(intents[0].hasCategory(Intent.CATEGORY_LAUNCHER))

            Intents.release()
        }

        ActivityScenario.launch(SearchActivity::class.java).use {
            Intents.init()

            onSearchInput().perform(typeText("o"))
            onListView().check(matches(withListSize(MaxShownItems.default)))

            onData(anything()).atPosition(0).perform(longClick())
            onAppInfoBtn(context).perform(click())
            val intents = Intents.getIntents()
            assertThat(intents.size, `is`(1))
            assertThat(
                intents[0].action.equals(Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
                `is`(true)
            )

            Intents.release()
        }

        ActivityScenario.launch(SearchActivity::class.java).use {
            Intents.init()

            onSearchInput().perform(typeText("typeup"))
            onData(anything()).atPosition(0).perform(click())

            onAppInfoBtn(context).perform(click())

            val intents = Intents.getIntents()
            assertThat(intents.size, `is`(1))
            assertThat(
                intents[0].action.equals(Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
                `is`(true)
            )

            Intents.release()
        }

    }

    private fun onAppInfoBtn(context : Context) = onView(
        allOf(
            withId(android.R.id.text1),
            withText(context.getString(R.string.appInfoTxt)),
            withParent(
                allOf(
                    withId(androidx.appcompat.R.id.select_dialog_listview),
                    withParent(withId(androidx.appcompat.R.id.contentPanel))
                )
            ),
            isDisplayed()
        )
    )
}