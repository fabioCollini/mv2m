# mv<sup>2</sup>m
Android MVVM lightweight library based on Android Data Binding

[![Circle CI](https://circleci.com/gh/fabioCollini/mv2m/tree/master.svg?style=svg)](https://circleci.com/gh/fabioCollini/mv2m/tree/master)
![JitPack](https://img.shields.io/github/tag/fabioCollini/mv2m.svg?label=JitPack)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-mv2m-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2524)

The goal of mv<sup>2</sup>m is to simplify JVM testing of an Android application.
The [demo module](/demo) contains a simple example of usage of mv<sup>2</sup>m library.
The [official app](https://github.com/commit-non-javisti/CoseNonJavisteAndroidApp) of the Italian blog [cosenonjaviste.it](http://cosenonjaviste.it) is a more complex example (built using Dagger and RxJava).
 
## Components of mv<sup>2</sup>m

##### Model
A Model class contains the data used to populate the user interface. It's saved automatically in Actvity/Fragment state, for this reason it must implement Parcelable interface.  

##### View
The View is an Activity or a Fragment, the user interface is managed using Android Data Binding. You need to extend ViewModelActivity
or ViewModelFragment class and implement createViewModel method returning a new ViewModel object.

##### ViewModel
The ViewModel is a subclass of ViewModel class, it is automatically saved in a retain fragment to avoid the creation of a new object on configuration change. 
The ViewModel is usually the object bound to the layout using the Data Binding framework.
It manages the background tasks and all the business logic of the application.  

![mv2m class diagram](/mv2m-class-diagram.png)

## JUnit tests
The ViewModel is not connected to the View, for this reason it's easily testable using a JVM test.
The demo module contains 
[some](https://github.com/fabioCollini/mv2m/blob/master/demo/src/test/java/it/cosenonjaviste/demomv2m/core/detail/NoteViewModelTest.java)
[JUnit tests](https://github.com/fabioCollini/mv2m/blob/master/demo/src/test/java/it/cosenonjaviste/demomv2m/core/list/NoteListViewModelTest.java)
 for ViewModel classes.

## Activity
Sometimes an Activity object is required to perform some operations, for example you need an Activity
object to show a SnackBar message or to start another Activity. In this case you can create a new class with
some methods with an Activity parameter:

```java
public class SnackbarMessageManager {

    @Override public void showMessage(Activity activity, int message) {
        if (activity != null) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }
}
```

An implementation of this class can be referenced from a ViewModel object, class ViewModel contains
a protected field containing a reference to the current Activity that can be used as parameter
(it's automatically updated every time there is a configuration change).
In this way it's easy to show a SnackBar from a ViewModel.
In a JVM test SnackbarMessageManager objects can be mocked to avoid dependencies on Android framework.

## ViewModel argument
ViewModel class has two generic parameters: the type of the argument and the type of the Model class.
The argument can be used when the ViewModel needs an argument, for example in a detail ViewModel the argument
is the id of the object you have to show. The argument must be an object that can be written in a Bundle
(a primitive type, a Serializable or a Parcelable).
Using ArgumentManager class you can start a new Activity passing an argument:

```java
ArgumentManager.startActivity(activity, NoteActivity.class, noteId);
```

ViewModel class contains a protected field argument that can be used to load the model data based on the argument.

In case you use Fragments as View objects you can use ArgumentManager.instantiateFragment method to
create a new Fragment and set the argument on it.

## Talk is cheap. Show me the code.

Let's see how to use mv<sup>2</sup>m to implement a simple example: a currency converter Activity.

![mv2m demo](/demo-mv2m.png)

The Model class contains two fields, one is bound with the input EditText and the other with the output TextField:

```java
public class CurrencyConverterModel implements Parcelable {

    public ObservableString input = new ObservableString();

    public ObservableString output = new ObservableString();

    //Parcelable implementation...
}
```

RateLoader is the class that load the rate from an external source (for example a REST service), in
this first implementation it execute everything synchronously:

```java
public class RateLoader {

    private static RateLoader INSTANCE = new RateLoader();

    private RateLoader() {
    }

    public static RateLoader instance() {
        return INSTANCE;
    }

    public float loadRate() {
        //...
    }
}
```

All the business logic is in the CurrencyConverterViewModel class, it extends ViewModel class defining
two generic parameters: Void (we don't need any argument) and the Model class.

```java
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
```

We use the dependency injection on the constructor to manage a reference to a RateLoader object.
The createModel method is overridden to create and return a new Model object.

The ViewModel class has no Android dependencies, it can be tested easily using a JUnit test:

```java
@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterViewModelTest {

    @Mock RateLoader rateLoader;

    @InjectMocks CurrencyConverterViewModel viewModel;

    @Test
    public void testConvertCurrency() {
        when(rateLoader.loadRate()).thenReturn(2f);

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("123");
        viewModel.calculate();

        assertThat(model.output.get()).isEqualTo("246,00");
    }
}
```

In the JUnit test we want to mock RateLoader object to write a repeatable test.

Data binding library is used to bind ViewModel object to the layout:

```xml
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable name="viewModel"
                  type="it.cosenonjaviste.demomv2m.core.currencyconverter1.CurrencyConverterViewModel"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">

        <EditText
            android:id="@+id/input"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:binding="@{viewModel.model.input}">
            <requestFocus/>
        </EditText>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="16dp"
            android:layout_marginTop="16dp"
            android:text="@string/convert"
            app:onClick="@{viewModel.calculate}"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{viewModel.model.output}"/>

    </LinearLayout>
</layout>
```

The app:binding and app:onClick are custom attributes, this [post](https://medium.com/@fabioCollini/android-data-binding-f9f9d3afc761)
explains how to create them.

The Activity is very simple, it extends ViewModelActivity, overrides createViewModel method,
creates the layout and binds it to the ViewModel:

```java
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
```

TODO...

```java
@VisibleForTesting
public static void setInstance(RateLoader instance) {
    RateLoader.INSTANCE = instance;
}
```

```java
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
```



```java
@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterViewModelTest {
    @Mock RateLoader rateLoader;

    @Mock MessageManager messageManager;

    @InjectMocks CurrencyConverterViewModel viewModel;

    @Test
    public void testConvertCurrencyOnError() {
        when(rateLoader.loadRate()).thenReturn(2f);

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("abc");
        viewModel.calculate();

        verify(messageManager).showMessage(R.string.conversion_error);
    }
}
```

```java
public class CurrencyConverterViewModel extends ViewModel<Void, CurrencyConverterModel> {

    private RateLoader rateLoader;

    private MessageManager messageManager;

    public CurrencyConverterViewModel(RateLoader rateLoader, MessageManager messageManager) {
        this.rateLoader = rateLoader;
        this.messageManager = messageManager;
        registerActivityAware(messageManager);
    }

    @NonNull @Override public CurrencyConverterModel createModel() {
        return new CurrencyConverterModel();
    }

    public void calculate() {
        String inputString = model.input.get();
        try {
            float input = Float.parseFloat(inputString);
            model.output.set(new DecimalFormat("0.00").format(input * rateLoader.loadRate()));
        } catch (NumberFormatException e) {
            messageManager.showMessage(R.string.conversion_error);
        }
    }
}
```

```java
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
        registerActivityAware(messageManager);
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
                        model.output.set(new DecimalFormat("0.00").format(input * rateLoader.loadRate()));
                    } catch (Exception e) {
                        uiExecutor.execute(new Runnable() {
                            @Override public void run() {
                                messageManager.showMessage(R.string.error_loading_rate);
                            }
                        });
                    } finally {
                        loading.set(false);
                    }
                }
            });
        } catch (NumberFormatException e) {
            messageManager.showMessage(R.string.conversion_error);
        }
    }
}
```

```java
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

    //...
}
```

```java
public class CurrencyConverterViewModel extends RxViewModel<Void, CurrencyConverterModel> {

    private RateLoader rateLoader;

    private MessageManager messageManager;

    public final ObservableBoolean loading = new ObservableBoolean();

    public CurrencyConverterViewModel(RateLoader rateLoader, MessageManager messageManager, SchedulerManager schedulerManager) {
        super(schedulerManager);
        this.rateLoader = rateLoader;
        this.messageManager = messageManager;
        registerActivityAware(messageManager);
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
                            model.output.set(new DecimalFormat("0.00").format(input * rate));
                        }
                    }, new Action1<Throwable>() {
                        @Override public void call(Throwable throwable) {
                            messageManager.showMessage(R.string.error_loading_rate);
                        }
                    });
        } catch (NumberFormatException e) {
            messageManager.showMessage(R.string.conversion_error);
        }
    }
}
```

```java
@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterViewModelTest {
    @Mock RateLoader rateLoader;

    @Mock MessageManager messageManager;

    @InjectMocks CurrencyConverterViewModel viewModel;

    @Test
    public void testConvertCurrency() {
        when(rateLoader.loadRate()).thenReturn(Observable.just(2f));

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("123");
        viewModel.calculate();

        assertThat(model.output.get()).isEqualTo("246,00");
    }

    @Test
    public void testConvertCurrencyLoadingError() {
        when(rateLoader.loadRate()).thenReturn(Observable.<Float>error(new RuntimeException()));

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("123");
        viewModel.calculate();

        verify(messageManager).showMessage(R.string.error_loading_rate);
    }

    @Test
    public void testConvertCurrencyOnError() {
        when(rateLoader.loadRate()).thenReturn(Observable.just(2f));

        CurrencyConverterModel model = viewModel.initAndResume();
        model.input.set("abc");
        viewModel.calculate();

        verify(messageManager).showMessage(R.string.conversion_error);
    }
}
```

## Work in progress

## License

    Copyright 2015 Fabio Collini

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
