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
package it.cosenonjaviste.demomv2m.ui;

import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.ui.detail.NoteActivity;
import it.cosenonjaviste.mv2m.ArgumentManager;
import it.cosenonjaviste.mv2m.Mv2mView;

public class ActivityNavigator implements Navigator {

    @Override public void openDetail(Mv2mView view, String noteId) {
        ArgumentManager.startActivityForResult(view.getActivity(), NoteActivity.class, OPEN_DETAIL, noteId);
    }
}
