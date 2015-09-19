package it.cosenonjaviste.demomv2m.ui;

import org.mockito.Mockito;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;

public class TestObjectFactory extends ObjectFactory {
    public TestObjectFactory() {
        noteLoaderService = Mockito.mock(NoteLoaderService.class);
        noteSaverService = Mockito.mock(NoteSaverService.class);
        navigator = Mockito.mock(ActivityNavigator.class);

        backgroundExecutor = new Executor() {
            @Override public void execute(Runnable command) {
                command.run();
            }
        };
    }
}
