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
package it.cosenonjaviste.demomv2m.core.currencyconverter1;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;

import it.cosenonjaviste.mv2m.ViewModel;

public class CurrencyConverterViewModel extends ViewModel<Void, CurrencyConverterModel> {

    private RateLoader rateLoader;

    public CurrencyConverterViewModel(RateLoader rateLoader) {
        this.rateLoader = rateLoader;
    }

    @NonNull @Override public CurrencyConverterModel createModel() {
        return new CurrencyConverterModel();
    }

    public void calculate() {
        String inputString = model.input.get();
        float input = Float.parseFloat(inputString);
        model.output.set(new DecimalFormat("0.00").format(input * rateLoader.loadRate()));
    }
}
