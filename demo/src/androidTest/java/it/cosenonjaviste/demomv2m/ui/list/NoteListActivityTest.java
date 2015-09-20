package it.cosenonjaviste.demomv2m.ui.list;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.TestData;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.TestObjectFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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
                .thenReturn(TestData.response());

        rule.launchActivity(null);

        onView(withText(TestData.TITLE_1)).check(matches(isDisplayed()));
    }

    @Test
    public void testReloadAfterError() {
        when(objectFactory.noteLoaderService().loadItems())
                .thenThrow(new RuntimeException())
                .thenReturn(TestData.response());

        rule.launchActivity(null);

        onView(withText(R.string.error_loading_list)).check(matches(isDisplayed()));

        onView(withText(R.string.retry)).perform(click());

        onView(withText(TestData.TITLE_1)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenDetail() {
        when(objectFactory.noteLoaderService().loadItems())
                .thenReturn(TestData.response());

        rule.launchActivity(null);
        onView(withText(TestData.TITLE_1)).perform(click());

        verify(objectFactory.singleton().navigator())
                .openDetail(any(NoteModel.class));
    }
}