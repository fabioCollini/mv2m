package it.cosenonjaviste.demomv2m.core;

import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.mv2m.ActivityAware;

public interface Navigator extends ActivityAware {
    void openDetail(NoteModel model);
}
