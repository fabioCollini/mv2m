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
package it.cosenonjaviste.mv2m;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.io.Serializable;

public class ArgumentManager {
    public static final String ARGUMENT = "argument";

    public static Object readArgument(Bundle arguments) {
        return arguments.get(ARGUMENT);
    }

    public static Intent writeArgument(Intent intent, Object argument) {
        if (argument != null) {
            if (argument instanceof Integer) {
                intent.putExtra(ARGUMENT, (Integer) argument);
            } else if (argument instanceof Float) {
                intent.putExtra(ARGUMENT, (Float) argument);
            } else if (argument instanceof Double) {
                intent.putExtra(ARGUMENT, (Double) argument);
            } else if (argument instanceof Long) {
                intent.putExtra(ARGUMENT, (Long) argument);
            } else if (argument instanceof Parcelable) {
                intent.putExtra(ARGUMENT, (Parcelable) argument);
            } else if (argument instanceof String) {
                intent.putExtra(ARGUMENT, (String) argument);
            } else if (argument instanceof Serializable) {
                intent.putExtra(ARGUMENT, (Serializable) argument);
            } else {
                throw new RuntimeException("Invalid argument of class " + argument.getClass() + ", it can't be stored in a bundle");
            }
        }
        return intent;
    }

    public static Bundle writeArgument(Bundle bundle, Object argument) {
        if (argument != null) {
            if (argument instanceof Integer) {
                bundle.putInt(ARGUMENT, (Integer) argument);
            } else if (argument instanceof Float) {
                bundle.putFloat(ARGUMENT, (Float) argument);
            } else if (argument instanceof Double) {
                bundle.putDouble(ARGUMENT, (Double) argument);
            } else if (argument instanceof Long) {
                bundle.putLong(ARGUMENT, (Long) argument);
            } else if (argument instanceof Parcelable) {
                bundle.putParcelable(ARGUMENT, (Parcelable) argument);
            } else if (argument instanceof String) {
                bundle.putString(ARGUMENT, (String) argument);
            } else if (argument instanceof Serializable) {
                bundle.putSerializable(ARGUMENT, (Serializable) argument);
            } else {
                throw new RuntimeException("Invalid argument of class " + argument.getClass() + ", it can't be stored in a bundle");
            }
        }
        return bundle;
    }

    /**
     * @deprecated Use {@link ActivityHolder#startActivity(Class, Object)} instead.
     */
    @Deprecated
    public static <ARG, VM extends ViewModel<ARG, ?>, A extends ViewModelActivity<VM>> void startActivity(Activity activity, Class<A> cls, ARG argument) {
        if (activity != null) {
            activity.startActivity(createIntent(activity, cls, argument));
        }
    }

    public static <ARG, VM extends ViewModel<ARG, ?>, F extends ViewModelFragment<VM>> F instantiateFragment(Activity activity, Class<F> cls, ARG argument) {
        Bundle args = new Bundle();
        writeArgument(args, argument);
        return (F) Fragment.instantiate(activity, cls.getName(), args);
    }

    /**
     * @deprecated Use {@link ActivityHolder#startActivityForResult(Class, int, Object)} instead.
     */
    @Deprecated
    public static <ARG, VM extends ViewModel<ARG, ?>, A extends ViewModelActivity<VM>> void startActivityForResult(Activity activity, Class<A> cls, int requestCode, ARG argument) {
        if (activity != null) {
            activity.startActivityForResult(createIntent(activity, cls, argument), requestCode);
        }
    }

    @NonNull public static <ARG, VM extends ViewModel<ARG, ?>, A extends ViewModelActivity<VM>> Intent createIntent(Activity activity, Class<A> cls, ARG argument) {
        return writeArgument(new Intent(activity, cls), argument);
    }
}
