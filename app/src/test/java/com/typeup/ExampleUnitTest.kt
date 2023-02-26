package com.typeup

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test() {
        MatcherAssert.assertThat(true, `is`(true))
    }

}