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

import android.support.annotation.VisibleForTesting;

import java.util.Random;

public class RateLoader {

    private static RateLoader INSTANCE = new RateLoader();

    private RateLoader() {
    }

    public static RateLoader instance() {
        return INSTANCE;
    }

    @VisibleForTesting
    public static void setInstance(RateLoader instance) {
        RateLoader.INSTANCE = instance;
    }

    public float loadRate() {
        return 1 + new Random().nextInt(300) / 1000f;
    }
}
