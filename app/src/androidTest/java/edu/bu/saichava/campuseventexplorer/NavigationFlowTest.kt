package edu.bu.saichava.campuseventexplorer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.bu.saichava.campuseventexplorer.ui.SearchFieldTestTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Covers the search-then-navigate-then-back flow the assignment asks to be verified by hand. */
@RunWith(AndroidJUnit4::class)
class NavigationFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchNarrowsTheList() {
        composeTestRule.onNodeWithTag(SearchFieldTestTag).performTextInput("robot")

        composeTestRule.onNodeWithText("Robotics Club Open Build").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jazz Ensemble Night").assertDoesNotExist()
    }

    @Test
    fun systemBackFromDetailRestoresTheSearchText() {
        composeTestRule.onNodeWithTag(SearchFieldTestTag).performTextInput("robot")
        composeTestRule.onNodeWithText("Robotics Club Open Build").performClick()

        composeTestRule.onNodeWithText("About this event").assertIsDisplayed()

        Espresso.pressBack()

        composeTestRule.onNodeWithTag(SearchFieldTestTag).assertTextContains("robot")
        composeTestRule.onNodeWithText("Robotics Club Open Build").assertIsDisplayed()
    }

    @Test
    fun appBarUpButtonReturnsToTheList() {
        composeTestRule.onNodeWithTag(SearchFieldTestTag).performTextInput("jazz")
        composeTestRule.onNodeWithText("Jazz Ensemble Night").performClick()

        composeTestRule.onNodeWithContentDescription("Back to events").performClick()

        composeTestRule.onNodeWithTag(SearchFieldTestTag).assertTextContains("jazz")
    }
}
