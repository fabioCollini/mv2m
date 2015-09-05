package it.cosenonjaviste.model;

import com.google.gson.Gson;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {

    public static final String JSON = "{\n" +
            "  \"id\": 123,\n" +
            "  \"title\": \"abc\",\n" +
            "  \"text\": \"def\"\n" +
            "}";

    @Test
    public void testParsing() {
        Note note = new Gson().fromJson(JSON, Note.class);
        assertThat(note).isNotNull();
        assertThat(note.getId()).isEqualTo(123L);
        assertThat(note.getTitle()).isEqualTo("abc");
        assertThat(note.getText()).isEqualTo("def");
    }
}
