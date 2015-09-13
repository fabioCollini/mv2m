package it.cosenonjaviste.demomv2m.ui.detail;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.TestObjectFactory;
import retrofit.RetrofitError;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoteActivityTest {
    @Rule public ActivityTestRule<NoteActivity> rule = new ActivityTestRule<>(NoteActivity.class, false, false);

    private NoteLoaderService noteLoaderService;
    private NoteSaverService noteSaverService;

    @Before
    public void setUp() throws Exception {
        ObjectFactory.setSingleton(new TestObjectFactory());
        noteLoaderService = ObjectFactory.singleton().noteLoaderService();
        noteSaverService = ObjectFactory.singleton().noteSaverService();

        when(noteLoaderService.load()).thenReturn(new Note(123, "title", "text"));
    }

    @Test
    public void testLoading() {
        rule.launchActivity(null);

        compileFormAndSave("newTitle", "newText");

        verify(noteSaverService).save(eq(123L), eq("newTitle"), eq("newText"));
    }

    @Test
    public void testReloadAfterError() {
        when(noteLoaderService.load())
                .thenThrow(RetrofitError.networkError("url", new IOException()))
                .thenReturn(new Note(123, "aaa", "bbb"));

        rule.launchActivity(null);

        onView(withText(R.string.retry)).perform(click());

        onView(withText(R.string.retry)).check(matches(not(isDisplayed())));

        onView(withText("aaa")).check(matches(isDisplayed()));
        onView(withText("bbb")).check(matches(isDisplayed()));
    }

    @Test
    public void testTitleValidation() {
        rule.launchActivity(null);

        compileFormAndSave("", "newText");

        onView(withText(R.string.mandatory_field)).check(matches(isDisplayed()));
    }

    @Test
    public void testTextValidation() {
        rule.launchActivity(null);

        compileFormAndSave("newTitle", "");

        onView(withText(R.string.mandatory_field)).check(matches(isDisplayed()));
    }

    private void compileFormAndSave(String title, String text) {
        onView(withId(R.id.title)).perform(replaceText(title));
        onView(withId(R.id.text)).perform(replaceText(text));

        onView(withId(R.id.save_button)).perform(click());
    }
}