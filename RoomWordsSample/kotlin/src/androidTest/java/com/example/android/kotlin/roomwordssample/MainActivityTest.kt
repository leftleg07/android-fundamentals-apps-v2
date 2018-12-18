package com.example.android.kotlin.roomwordssample

import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    //    @get:Rule
//    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)
    @get:Rule
    var intentsRule = IntentsTestRule(MainActivity::class.java)


    private lateinit var mWordDao: WordDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        val db = WordRoomDatabase.getDatabase(context)
        mWordDao = db.wordDao()
    }

    @Test
    fun open_activity() {
        while (!intentsRule.activity.isFinishing) {
            sleep(1000)
        }
    }

    @Test
    fun empty_word() {
        mWordDao.deleteAll()
        while (!intentsRule.activity.isFinishing) {
            sleep(1000)
        }
    }

    // insert word - word
    @Test
    fun insert_word() {
        val classComponent = hasComponent(hasShortClassName(".NewWordActivity"))
        val resultData = Intent().putExtra(NewWordActivity.EXTRA_REPLY, "elephant")
        val result = Instrumentation.ActivityResult(AppCompatActivity.RESULT_OK, resultData)

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(classComponent).respondWith(result)

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.fab)).perform(click())

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(classComponent)

        while (!intentsRule.activity.isFinishing) {
            sleep(1000)
        }

    }

    // insert empty word
    @Test
    fun insert_empty_word() {
        val classComponent = hasComponent(hasShortClassName(".NewWordActivity"))
        val resultData = Intent()
        val result = Instrumentation.ActivityResult(AppCompatActivity.RESULT_CANCELED, resultData)

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(classComponent).respondWith(result)

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.fab)).perform(click())

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(classComponent)

        while (!intentsRule.activity.isFinishing) {
            sleep(1000)
        }

    }

}