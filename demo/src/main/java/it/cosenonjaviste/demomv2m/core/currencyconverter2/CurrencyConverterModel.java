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
package it.cosenonjaviste.demomv2m.core.currencyconverter2;

import android.os.Parcel;
import android.os.Parcelable;

import it.cosenonjaviste.twowaydatabinding.ObservableString;

public class CurrencyConverterModel implements Parcelable {

    public ObservableString input = new ObservableString();

    public ObservableString output = new ObservableString();

    public CurrencyConverterModel() {
    }

    protected CurrencyConverterModel(Parcel in) {
        input = in.readParcelable(ObservableString.class.getClassLoader());
        output = in.readParcelable(ObservableString.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(input, flags);
        dest.writeParcelable(output, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurrencyConverterModel> CREATOR = new Creator<CurrencyConverterModel>() {
        @Override
        public CurrencyConverterModel createFromParcel(Parcel in) {
            return new CurrencyConverterModel(in);
        }

        @Override
        public CurrencyConverterModel[] newArray(int size) {
            return new CurrencyConverterModel[size];
        }
    };
}
