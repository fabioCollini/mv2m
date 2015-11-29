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
package it.cosenonjaviste.demomv2m.core.currencyconverter3;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.MessageManager;
import it.cosenonjaviste.mv2m.ViewModel;

public class CurrencyConverterViewModel extends ViewModel<Void, CurrencyConverterModel> {

    private RateLoader rateLoader;

    private MessageManager messageManager;

    private Executor ioExecutor;

    private Executor uiExecutor;

    public final ObservableBoolean loading = new ObservableBoolean();

    public CurrencyConverterViewModel(RateLoader rateLoader, MessageManager messageManager, Executor ioExecutor, Executor uiExecutor) {
        this.rateLoader = rateLoader;
        this.messageManager = messageManager;
        this.ioExecutor = ioExecutor;
        this.uiExecutor = uiExecutor;
    }

    @NonNull @Override public CurrencyConverterModel createModel() {
        return new CurrencyConverterModel();
    }

    public void calculate() {
        String inputString = model.input.get();
        try {
            final float input = Float.parseFloat(inputString);
            loading.set(true);
            ioExecutor.execute(new Runnable() {
                @Override public void run() {
                    try {
                        DecimalFormat decimalFormat = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
                        model.output.set(decimalFormat.format(input * rateLoader.loadRate()));
                    } catch (Exception e) {
                        uiExecutor.execute(new Runnable() {
                            @Override public void run() {
                                messageManager.showMessage(activityHolder, R.string.error_loading_rate);
                            }
                        });
                    } finally {
                        loading.set(false);
                    }
                }
            });
        } catch (NumberFormatException e) {
            messageManager.showMessage(activityHolder, R.string.conversion_error);
        }
    }
}
