package it.cosenonjaviste.demomv2m.model;

import java.util.Arrays;
import java.util.List;

public class NoteListResponse {
    private List<Note> results;

    public NoteListResponse() {
    }

    public NoteListResponse(Note... results) {
        this.results = Arrays.asList(results);
    }

    public List<Note> getResults() {
        return results;
    }
}
