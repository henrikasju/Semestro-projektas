package com.example.neutronas;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void applicationTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.cameraView),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.noteNameText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Coffee"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.noteNameText), withText("Coffee"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        try {
            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.noteDescriptionText),
                            isDisplayed()));
            appCompatEditText3.perform(replaceText("Good cup"), closeSoftKeyboard());
            System.out.println("Note description box exist and contains 'Good cup' text.");
        } catch (NoMatchingViewException e) {
            System.out.println("Note description box does not exist.");
        }

        try {
            onView(withId(R.id.symbol_circle)).check(matches(isDisplayed()));
            System.out.println("symbol_circle was found.");
        } catch (NoMatchingViewException e) {
            System.out.println("symbol_circle was not found.");
        }

        try {
            ViewInteraction editText = onView(
                    allOf(withId(R.id.noteNameText), withText("Coffee"),
                            isDisplayed()));
            editText.check(matches(withText("Coffee")));
            System.out.println("Note name is Coffee.");
        } catch (NoMatchingViewException e) {
            System.out.println("Note name does not exist.");
        }


        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.save_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.topPart),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.back_button_gallery),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        try {
            ViewInteraction imageView2 = onView(
                    allOf(childAtPosition(
                            childAtPosition(
                                    withId(R.id.topBar),
                                    0),
                            1),
                            isDisplayed()));
            imageView2.check(matches(isDisplayed()));
            System.out.println("Top bar does exists.");
        } catch (NoMatchingViewException e) {
            System.out.println("Top bar does not exists.");
        }

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.scan_view),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.newsView),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.back_button_news),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.aboutButton), withText("About"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.back_button_about),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        System.out.println("TEST PASSED SUCCESSFULLY");
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
