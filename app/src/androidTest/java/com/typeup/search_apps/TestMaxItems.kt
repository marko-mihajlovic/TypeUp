package com.typeup.search_apps

import android.view.InputDevice
import android.view.MotionEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.util.CustomListViewMatcher.withListSize
import com.typeup.util.SharedPref
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

@RunWith(AndroidJUnit4::class)
class TestMaxItems {

    private fun onAcceptBtn() = onView(withText("Accept"))
    private fun onSearchInput() = onView(withId(R.id.searchInput))
    private fun onListView() = onView(withId(R.id.listView))
    private fun onOptionsBtn() = onView(withId(R.id.optionsBtn))
    private fun onMaxItemsBtn() = onView(withText(R.string.maxItemsTxt))
    private fun onNumPicker() = onView(withId(R.id.dialog_number_picker))
    private fun onSaveBtn() = onView(withText(R.string.saveTxt))

    @Test
    fun test_max_items() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        SharedPref.edit(context).clear().commit()

        ActivityScenario.launch(SearchActivity::class.java).use {
            onAcceptBtn().perform(ViewActions.click())

            onSearchInput().perform(ViewActions.typeText("o"))
            onListView().check(matches(withListSize(MaxShownItems.default)))

            changeMaxItems(delta = -1, expected = MaxShownItems.default - 1)
            changeMaxItems(delta = 1, expected = MaxShownItems.default)
            changeMaxItems(delta = 1, expected = MaxShownItems.default + 1)
            changeMaxItems(delta = -1, expected = MaxShownItems.default)

            changeMaxItems(delta = MaxShownItems.max, expected = MaxShownItems.max)
            changeMaxItems(delta = -MaxShownItems.max, expected = MaxShownItems.min)

            it.close()
        }

    }

    private fun changeMaxItems(delta: Int, expected: Int) {
        if (delta > 0)
            increaseMaxItems(delta, expected)
        else if (delta < 0)
            decreaseMaxItems(abs(delta), expected)
    }

    private fun decreaseMaxItems(times: Int, expected: Int) {
        openMaxItemsSettingsChangeValueAndAssertExpected(
            GeneralLocation.TOP_CENTER,
            times,
            expected
        )
    }

    private fun increaseMaxItems(times: Int, expected: Int) {
        openMaxItemsSettingsChangeValueAndAssertExpected(
            GeneralLocation.BOTTOM_CENTER,
            times,
            expected
        )
    }

    private fun openMaxItemsSettingsChangeValueAndAssertExpected(
        location: GeneralLocation,
        repeat: Int,
        expected: Int
    ) {
        onOptionsBtn().perform(ViewActions.click())
        onMaxItemsBtn().perform(ViewActions.click())

        repeat(repeat) {
            onNumPicker()
                .check(matches(isDisplayed()))
                .perform(clickAtCustomLocation(location))
        }

        onSaveBtn()
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())

        onListView().check(matches(withListSize(expected)))
    }

    private fun clickAtCustomLocation(coordinatesProvider: CoordinatesProvider): ViewAction {
        return actionWithAssertions(
            GeneralClickAction(
                Tap.SINGLE,
                coordinatesProvider,
                Press.FINGER,
                InputDevice.SOURCE_UNKNOWN,
                MotionEvent.BUTTON_PRIMARY
            )
        )
    }

}