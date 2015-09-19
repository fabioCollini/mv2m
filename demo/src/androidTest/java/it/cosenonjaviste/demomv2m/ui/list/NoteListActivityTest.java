package it.cosenonjaviste.demomv2m.ui.list;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteListResponse;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.TestObjectFactory;
import it.cosenonjaviste.demomv2m.ui.detail.NoteActivity;
import it.cosenonjaviste.mv2m.ViewModelManager;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class NoteListActivityTest {

    @Rule public ActivityTestRule<NoteListActivity> rule = new ActivityTestRule<>(NoteListActivity.class, false, false);

    private TestObjectFactory objectFactory;

    @Before
    public void setUp() throws Exception {
        objectFactory = new TestObjectFactory();
        ObjectFactory.setSingleton(objectFactory);
    }

    @Test
    public void testLoadList() {
        when(objectFactory.noteLoaderService().loadItems())
                .thenReturn(new NoteListResponse(new Note("1", "abcdef"), new Note("2", "b")));

        rule.launchActivity(null);

        onView(withText("abcdef")).check(matches(isDisplayed()));
    }

    @Test
    public void testReloadAfterError() {
        when(objectFactory.noteLoaderService().loadItems())
                .thenThrow(new RuntimeException())
                .thenReturn(new NoteListResponse(new Note("1", "abcdef"), new Note("2", "b")));

        rule.launchActivity(null);

        onView(withText(R.string.error_loading_list)).check(matches(isDisplayed()));

        onView(withText(R.string.retry)).perform(click());

        onView(withText("abcdef")).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenDetail() {
        when(objectFactory.noteLoaderService().loadItems())
                .thenReturn(new NoteListResponse(new Note("1", "abcdef"), new Note("2", "b")));

//        when(objectFactory.noteLoaderService().load(anyString())).thenReturn(new Note("123", "abcdef", "text"));

        intending(allOf(hasComponent(NoteActivity.class.getName())))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        rule.launchActivity(null);

        onView(withText("aaaa")).perform(click());

        intended(allOf(hasComponent(NoteActivity.class.getName()), hasExtra(ViewModelManager.MODEL, new NoteModel("1"))));
    }
}