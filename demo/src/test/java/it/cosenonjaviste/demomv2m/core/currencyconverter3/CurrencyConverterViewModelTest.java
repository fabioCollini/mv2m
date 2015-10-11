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

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.MessageManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterViewModelTest {
    @Mock RateLoader rateLoader;

    @Mock MessageManager messageManager;

    @Spy Executor executor = new Executor() {
        @Override public void execute(Runnable command) {
            command.run();
        }
    };

    @InjectMocks CurrencyConverterViewModel viewModel;

    @Test
    public void testConvertCurrency() {
        when(rateLoader.loadRate()).thenReturn(2f);

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("123");
        viewModel.calculate();

        assertThat(model.output.get()).isEqualTo("246,00");
    }

    @Test
    public void testConvertCurrencyOnError() {
        when(rateLoader.loadRate()).thenReturn(2f);

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("abc");
        viewModel.calculate();

        verify(messageManager).showMessage(any(Activity.class), eq(R.string.conversion_error));
    }
}