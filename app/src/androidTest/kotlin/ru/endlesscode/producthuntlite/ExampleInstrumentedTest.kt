package ru.endlesscode.producthuntlite

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        Assert.assertEquals("ru.endlesscode.producthuntlite", appContext.packageName)
    }
}