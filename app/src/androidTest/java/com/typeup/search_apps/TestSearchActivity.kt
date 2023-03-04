package com.typeup.search_apps

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
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSearchActivity {

    @Test
    fun test_activity() {

        val searchInput = withId(R.id.searchInput)
        val listView = withId(R.id.listView)
        val msgTxt = withId(R.id.msgTxt)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        SharedPref.edit(context).clear().commit()

        ActivityScenario.launch(SearchActivity::class.java).use {

            val acceptBtn = withText("Accept")
            onView(acceptBtn).perform(click())

            onView(listView).check(isCompletelyBelow(searchInput))

            onView(searchInput).perform(typeText("gm")) // gmail
            onView(listView).check(matches(withListSize(1)))

            onView(searchInput).perform(clearText())

            onView(searchInput).perform(typeText("non_existent_app"))
            onView(listView).check(matches(withListSize(0)))
            onView(msgTxt).check(matches(withText("No results found.")))
            onView(msgTxt).check(isCompletelyBelow(searchInput))

            onView(searchInput).perform(clearText())

            onView(searchInput).perform(typeText("a"))
            onView(listView).check(matches(withListSize(MaxShownItems.default)))
        }

        ActivityScenario.launch(SearchActivity::class.java).use {
            Intents.init()

            onView(searchInput).perform(typeText("o"))
            onView(listView).check(matches(withListSize(MaxShownItems.default)))

            onData(anything()).atPosition(0).perform(click())
            val intents = Intents.getIntents()
            assertThat(intents.size, `is`(1))
            assertThat(intents[0].action.equals(Intent.ACTION_MAIN), `is`(true))
            assertThat(intents[0].hasCategory(Intent.CATEGORY_LAUNCHER), `is`(true))

            Intents.release()
        }

        ActivityScenario.launch(SearchActivity::class.java).use {
            Intents.init()

            onView(searchInput).perform(typeText("typeup"))
            onData(anything()).atPosition(0).perform(click())

            val appInfoBtn = onView(
                allOf(
                    withId(android.R.id.text1), withText(context.getString(R.string.appInfoTxt)),
                    withParent(
                        allOf(
                            withId(androidx.appcompat.R.id.select_dialog_listview),
                            withParent(withId(androidx.appcompat.R.id.contentPanel))
                        )
                    ),
                    isDisplayed()
                )
            )
            appInfoBtn
                .check(matches(withText(context.getString(R.string.appInfoTxt))))
                .perform(click())

            val intents = Intents.getIntents()
            assertThat(intents.size, `is`(1))
            assertThat(
                intents[0].action.equals(Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
                `is`(true)
            )

            Intents.release()
        }

    }

}