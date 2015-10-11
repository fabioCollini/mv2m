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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.currencyconverter1.RateLoader;
import it.cosenonjaviste.demomv2m.utils.ViewModelActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

public class CurrencyConverterActivityTest {

    @Rule public ViewModelActivityTestRule<Void> rule = new ViewModelActivityTestRule<>(CurrencyConverterActivity.class);

    private RateLoader rateLoader;

    @Before
    public void setUp() {
        rateLoader = Mockito.mock(RateLoader.class);
        RateLoader.setInstance(rateLoader);
    }

    @Test
    public void testConversion() {
        when(rateLoader.loadRate()).thenReturn(2f);

        rule.launchActivity();

        onView(withId(R.id.input)).perform(typeText("123"));

        onView(withText(R.string.convert)).perform(click());

        onView(withText("246,00")).check(matches(isDisplayed()));
    }
}