package com.typeup.home

import android.view.View
import android.widget.ListView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.R
import com.typeup.options.MaxShownItems
import com.typeup.util.getSharedPref
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestSearchActivity {

    @Test
    fun test_activity() {

        val searchInput = withId(R.id.searchInput)
        val listView = withId(R.id.listView)
        val msgTxt = withId(R.id.msgTxt)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        getSharedPref(context).edit().clear().commit()

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

            onView(searchInput).perform(typeText("o"))
            onView(listView).check(matches(withListSize(MaxShownItems.default)))

        }

    }

}

fun withListSize(size: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun matchesSafely(view: View): Boolean {
            return (view as ListView).count == size
        }

        override fun describeTo(description: Description) {
            description.appendText("ListView should have $size items")
        }
    }
}
