package it.cosenonjaviste.demomv2m.model;

import com.google.gson.Gson;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {

    public static final String JSON = "{\n" +
            "  \"objectId\": \"123\",\n" +
            "  \"title\": \"abc\",\n" +
            "  \"text\": \"def\"\n" +
            "}";
    public static final String LIST_JSON = "{\n" +
            "    \"results\": [\n" +
            "        {\n" +
            "            \"createdAt\": \"2015-09-18T06:50:50.178Z\",\n" +
            "            \"objectId\": \"01sOuzHFFG\",\n" +
            "            \"text\": \"bbbbb\",\n" +
            "            \"title\": \"aaaa\",\n" +
            "            \"updatedAt\": \"2015-09-18T06:50:55.839Z\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Test
    public void testParsingList() {
        NoteListResponse response = new Gson().fromJson(LIST_JSON, NoteListResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).hasSize(1);
        assertThat(response.getResults().get(0))
                .isEqualToComparingFieldByField(new Note("01sOuzHFFG", "aaaa", "bbbbb"));
    }

    @Test
    public void testParsingNote() {
        Note note = new Gson().fromJson(JSON, Note.class);
        assertThat(note).isNotNull();
        assertThat(note.getObjectId()).isEqualTo("123");
        assertThat(note.getTitle()).isEqualTo("abc");
        assertThat(note.getText()).isEqualTo("def");
    }
}
