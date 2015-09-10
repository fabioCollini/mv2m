package it.cosenonjaviste.demomv2m.core;

public class NoteViewSpy implements NoteView {
    public int message;

    @Override public void showMessage(int message) {
        this.message = message;
    }
}
