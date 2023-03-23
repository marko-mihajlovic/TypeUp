package com.typeup.util

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.typeup.search_apps.SearchActivity

object CustomTestWrappers {

    fun wrapSearchActivityTest(test: () -> Unit) {
        SharedPref.edit().clear().commit()

        ActivityScenario.launch(SearchActivity::class.java).use {
            onView(withText("Accept")).perform(ViewActions.click())

            test()
        }
    }

    fun wrapSearchActivityIntentTest(test: () -> Unit) {
        wrapSearchActivityTest {
            Intents.init()
            test()
            Intents.release()
        }
    }

}