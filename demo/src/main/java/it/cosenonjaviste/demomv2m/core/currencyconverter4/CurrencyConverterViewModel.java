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
package it.cosenonjaviste.demomv2m.core.currencyconverter4;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.MessageManager;
import it.cosenonjaviste.mv2m.rx.RxViewModel;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.functions.Action1;

public class CurrencyConverterViewModel extends RxViewModel<Void, CurrencyConverterModel> {

    private RateLoader rateLoader;

    private MessageManager messageManager;

    public final ObservableBoolean loading = new ObservableBoolean();

    public CurrencyConverterViewModel(RateLoader rateLoader, MessageManager messageManager, SchedulerManager schedulerManager) {
        super(schedulerManager);
        this.rateLoader = rateLoader;
        this.messageManager = messageManager;
    }

    @NonNull @Override public CurrencyConverterModel createModel() {
        return new CurrencyConverterModel();
    }

    public void calculate() {
        String inputString = model.input.get();
        try {
            final float input = Float.parseFloat(inputString);
            subscribe(
                    new Action1<Boolean>() {
                        @Override public void call(Boolean b) {
                            loading.set(b);
                        }
                    },
                    rateLoader.loadRate(),
                    new Action1<Float>() {
                        @Override public void call(Float rate) {
                            DecimalFormat decimalFormat = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
                            model.output.set(decimalFormat.format(input * rate));
                        }
                    }, new Action1<Throwable>() {
                        @Override public void call(Throwable throwable) {
                            messageManager.showMessage(view, R.string.error_loading_rate);
                        }
                    });
        } catch (NumberFormatException e) {
            messageManager.showMessage(view, R.string.conversion_error);
        }
    }
}
