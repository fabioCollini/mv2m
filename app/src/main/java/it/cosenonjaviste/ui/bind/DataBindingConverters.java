package it.cosenonjaviste.ui.bind;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.utils.ObservableString;
import it.cosenonjaviste.ui.utils.TextWatcherAdapter;

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

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(view.getContext()).load(url).into(view);
        } else {
            view.setImageDrawable(null);
        }
    }

    @BindingAdapter({"app:textHtml"})
    public static void bindHtmlText(TextView view, String text) {
        view.setText(Html.fromHtml(text));
    }

    @BindingAdapter({"app:onClick"})
    public static void bindOnClick(View view, Runnable listener) {
        view.setOnClickListener(v -> listener.run());
    }

    @BindingAdapter({"app:url"})
    public static void bindOnClick(WebView view, String url) {
        view.loadUrl(url);
    }
}
