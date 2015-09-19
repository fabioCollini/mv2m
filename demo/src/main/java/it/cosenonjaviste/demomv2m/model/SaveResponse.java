package it.cosenonjaviste.demomv2m.model;

public class SaveResponse {
    private String objectId;

    SaveResponse() {
    }

    public SaveResponse(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }
}
