package it.cosenonjaviste.demomv2m.ui;

import org.mockito.Mockito;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.model.NoteLoader;
import it.cosenonjaviste.demomv2m.model.NoteSaver;

public class TestObjectFactory extends ObjectFactory {
    public TestObjectFactory() {
        noteLoader = Mockito.mock(NoteLoader.class);
        noteSaver = Mockito.mock(NoteSaver.class);
        navigator = Mockito.mock(ActivityNavigator.class);

        backgroundExecutor = new Executor() {
            @Override public void execute(Runnable command) {
                command.run();
            }
        };
    }
}
