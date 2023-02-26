package com.typeup.home

import android.view.View
import android.widget.ListView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.typeup.R
import com.typeup.options.MaxShownItems
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestSearchActivity {

    @get:Rule
    val activityRule = ActivityScenarioRule(SearchActivity::class.java)

    @Test
    fun test() {
        val searchInput = withId(R.id.searchInput)
        val listView = withId(R.id.listView)
        val msgTxt = withId(R.id.msgTxt)

        onView(listView).check(isCompletelyBelow(searchInput))

        onView(searchInput).perform(typeText("gm")) // gmail
        onView(listView).check(matches(withListSize(1)))

        onView(searchInput).perform(clearText())

        onView(searchInput).perform(typeText("non_existent_app"))
        onView(listView).check(matches(withListSize(0)))
        onView(msgTxt).check(matches(withText("No results found.")))

        onView(searchInput).perform(clearText())

        onView(searchInput).perform(typeText("a"))
        onView(listView).check(matches(withListSize(MaxShownItems.default)))
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
