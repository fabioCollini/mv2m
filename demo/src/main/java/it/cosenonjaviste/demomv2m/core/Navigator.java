package it.cosenonjaviste.demomv2m.core;

import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.mv2m.ActivityAware;

public interface Navigator extends ActivityAware {
    int OPEN_DETAIL = 123;

    void openDetail(NoteModel model);
}
