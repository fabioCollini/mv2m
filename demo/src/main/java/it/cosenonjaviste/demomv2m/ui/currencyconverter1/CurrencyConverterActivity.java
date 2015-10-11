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
package it.cosenonjaviste.demomv2m.ui.currencyconverter1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.currencyconverter1.CurrencyConverterViewModel;
import it.cosenonjaviste.demomv2m.core.currencyconverter1.RateLoader;
import it.cosenonjaviste.demomv2m.databinding.CurrencyConverterBinding;
import it.cosenonjaviste.mv2m.ViewModelActivity;

public class CurrencyConverterActivity extends ViewModelActivity<CurrencyConverterViewModel> {

    @Override public CurrencyConverterViewModel createViewModel() {
        return new CurrencyConverterViewModel(RateLoader.instance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrencyConverterBinding binding = DataBindingUtil.setContentView(this, R.layout.currency_converter);
        binding.setViewModel(viewModel);
    }
}
