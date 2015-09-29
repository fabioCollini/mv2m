package it.cosenonjaviste.demomv2m.ui.detail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.TestData;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoader;
import it.cosenonjaviste.demomv2m.model.NoteSaver;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.TestObjectFactory;
import it.cosenonjaviste.demomv2m.utils.ViewModelActivityTestRule;
import retrofit.RetrofitError;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoteActivityTest {
    @Rule public ViewModelActivityTestRule<String> rule = new ViewModelActivityTestRule<>(NoteActivity.class);

    private NoteLoader noteLoader;
    private NoteSaver noteSaver;

    @Before
    public void setUp() throws Exception {
        ObjectFactory.setSingleton(new TestObjectFactory());
        noteLoader = ObjectFactory.singleton().noteLoaderService();
        noteSaver = ObjectFactory.singleton().noteSaverService();

        when(noteLoader.load(anyString())).thenReturn(TestData.noteA());
    }

    @Test
    public void testLoading() {
        rule.launchActivity(TestData.ID_A);

        compileFormAndSave(TestData.NEW_TITLE, TestData.NEW_TEXT);

        verify(noteSaver).save(eq(TestData.ID_A), any(Note.class));
    }

    @Test
    public void testReloadAfterError() {
        when(noteLoader.load(anyString()))
                .thenThrow(RetrofitError.networkError("url", new IOException()))
                .thenReturn(TestData.noteA());

        rule.launchActivity(TestData.ID_A);

        onView(withText(R.string.retry)).perform(click());

        onView(withText(R.string.retry)).check(matches(not(isDisplayed())));

        onView(withText(TestData.TITLE_A)).check(matches(isDisplayed()));
        onView(withText(TestData.TEXT_A)).check(matches(isDisplayed()));
    }

    @Test
    public void testTitleValidation() {
        rule.launchActivity(TestData.ID_A);

        compileFormAndSave("", TestData.NEW_TEXT);

        onView(withText(R.string.mandatory_field)).check(matches(isDisplayed()));
    }

    @Test
    public void testTextValidation() {
        rule.launchActivity(TestData.ID_A);

        compileFormAndSave(TestData.NEW_TITLE, "");

        onView(withText(R.string.mandatory_field)).check(matches(isDisplayed()));
    }

    private void compileFormAndSave(String title, String text) {
        onView(withId(R.id.title)).perform(replaceText(title));
        onView(withId(R.id.text)).perform(replaceText(text));

        onView(withId(R.id.save_button)).perform(scrollTo(), click());
    }
}