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
package it.cosenonjaviste.demomv2m.ui.bind;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.utils.ObservableString;

public class DataBindingConverters {

    @BindingAdapter({"app:error"})
    public static void bindValidationError(TextInputLayout textInputLayout, int errorRes) {
        if (errorRes != 0) {
            textInputLayout.setError(textInputLayout.getResources().getString(errorRes));
        } else {
            textInputLayout.setError(null);
        }
    }

    @BindingAdapter({"app:binding"})
    public static void bindEditText(EditText view, final ObservableString observableString) {
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.addTextChangedListener(new TextWatcherAdapter() {
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    observableString.set(s.toString());
                }
            });
        }
        String newValue = observableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter({"app:visibleOrGone"})
    public static void bindVisibleOrGone(View view, boolean b) {
        view.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"app:visible"})
    public static void bindVisible(View view, boolean b) {
        view.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter({"app:onClick"})
    public static void bindOnClick(View view, final Runnable listener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.run();
            }
        });
    }

    @BindingConversion
    public static String convertObservableStringToString(ObservableString s) {
        return s.get();
    }
}
