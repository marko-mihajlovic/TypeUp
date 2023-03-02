package com.typeup.home

import android.view.InputDevice
import android.view.MotionEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.util.SharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMaxItems {

    private fun onAcceptBtn() = onView(withText("Accept"))

    @Test
    fun test_max_items(): Unit = runBlocking {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        SharedPref.edit(context).clear().commit()

        ActivityScenario.launch(SearchActivity::class.java).use {
            onAcceptBtn().perform(ViewActions.click())

            val searchInput = withId(R.id.searchInput)
            val listView = withId(R.id.listView)
            val optionsBtn = withId(R.id.optionsBtn)

            onView(searchInput).perform(ViewActions.typeText("o"))
            delay(100)
            onView(listView).check(matches(withListSize(MaxShownItems.default)))

            onView(optionsBtn).perform(ViewActions.click())

            val maxItemsOption = withText(R.string.maxItemsTxt)
            onView(maxItemsOption).perform(ViewActions.click())


            val picker = withId(R.id.dialog_number_picker)
            onView(picker)
                .check(matches(isDisplayed()))
                .perform(clickTopCentre)

            val saveBtn = withText(R.string.saveTxt)
            onView(saveBtn)
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

            onView(listView)
                .check(matches(withListSize(MaxShownItems.default - 1)))

            it.close()
        }

    }

    private val clickTopCentre =
        actionWithAssertions(
            GeneralClickAction(
                Tap.SINGLE,
                GeneralLocation.TOP_CENTER,
                Press.FINGER,
                InputDevice.SOURCE_UNKNOWN,
                MotionEvent.BUTTON_PRIMARY
            )
        )

    private val clickBottomCentre =
        actionWithAssertions(
            GeneralClickAction(
                Tap.SINGLE,
                GeneralLocation.BOTTOM_CENTER,
                Press.FINGER,
                InputDevice.SOURCE_UNKNOWN,
                MotionEvent.BUTTON_PRIMARY
            )
        )
}