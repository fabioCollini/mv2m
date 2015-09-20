package it.cosenonjaviste.demomv2m.ui.detail;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.TestData;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.TestObjectFactory;
import it.cosenonjaviste.mv2m.ViewModelManager;
import retrofit.RetrofitError;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
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
    @Rule public ActivityTestRule<NoteActivity> rule = new ActivityTestRule<>(NoteActivity.class, false, false);

    private NoteLoaderService noteLoaderService;
    private NoteSaverService noteSaverService;

    @Before
    public void setUp() throws Exception {
        ObjectFactory.setSingleton(new TestObjectFactory());
        noteLoaderService = ObjectFactory.singleton().noteLoaderService();
        noteSaverService = ObjectFactory.singleton().noteSaverService();

        when(noteLoaderService.load(anyString())).thenReturn(TestData.noteA());
    }

    @Test
    public void testLoading() {
        rule.launchActivity(new Intent().putExtra(ViewModelManager.MODEL, new NoteModel(TestData.ID_A)));

        compileFormAndSave(TestData.NEW_TITLE, TestData.NEW_TEXT);

        verify(noteSaverService).save(eq(TestData.ID_A), any(Note.class));
    }

    @Test
    public void testReloadAfterError() {
        when(noteLoaderService.load(anyString()))
                .thenThrow(RetrofitError.networkError("url", new IOException()))
                .thenReturn(TestData.noteA());

        rule.launchActivity(new Intent().putExtra(ViewModelManager.MODEL, new NoteModel(TestData.ID_A)));

        onView(withText(R.string.retry)).perform(click());

        onView(withText(R.string.retry)).check(matches(not(isDisplayed())));

        onView(withText(TestData.TITLE_A)).check(matches(isDisplayed()));
        onView(withText(TestData.TEXT_A)).check(matches(isDisplayed()));
    }

    @Test
    public void testTitleValidation() {
        rule.launchActivity(new Intent().putExtra(ViewModelManager.MODEL, new NoteModel(TestData.ID_A)));

        compileFormAndSave("", TestData.NEW_TEXT);

        onView(withText(R.string.mandatory_field)).check(matches(isDisplayed()));
    }

    @Test
    public void testTextValidation() {
        rule.launchActivity(new Intent().putExtra(ViewModelManager.MODEL, new NoteModel(TestData.ID_A)));

        compileFormAndSave(TestData.NEW_TITLE, "");

        onView(withText(R.string.mandatory_field)).check(matches(isDisplayed()));
    }

    private void compileFormAndSave(String title, String text) {
        onView(withId(R.id.title)).perform(replaceText(title));
        onView(withId(R.id.text)).perform(replaceText(text));

        onView(withId(R.id.save_button)).perform(click());
    }
}