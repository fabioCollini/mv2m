/*
 *  Copyright 2015 Fabio Collini.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
