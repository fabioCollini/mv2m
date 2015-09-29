# mv<sup>2</sup>m
Android MVVM lightweight library based on Android Data Binding

[![Circle CI](https://circleci.com/gh/fabioCollini/mv2m/tree/master.svg?style=svg)](https://circleci.com/gh/fabioCollini/mv2m/tree/master)
![JitPack](https://img.shields.io/github/tag/fabioCollini/mv2m.svg?label=JitPack)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-mv2m-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2524)

The goal of mv<sup>2</sup>m is to simplify JVM testing of an Android application.
 
## Components of mv<sup>2</sup>m

##### Model
A Model class contains the data used to populate the user interface. It's saved automatically in Actvity/Fragment state, for this reason it must implement Parcelable interface.  

##### View
The View is an Activity or a Fragment, the user interface is managed using Android Data Binding.

##### ViewModel
The ViewModel is a subclass of ViewModel class, it is automatically saved in a retain fragment to avoid the creation of a new object on configuration change. 
The ViewModel is usually the object bound to the layout using the Data Binding framework.
It manages the background tasks and all the business logic of the application.  

![mv2m class diagram](/mv2m-class-diagram.png)

## JUnit test
The ViewModel is not connected to the View, for this reason it's easily testable using a JVM test.

<!--
## ActivityAware

passaggio parametri come model

esempio converter euro dollaro
viewmodel, model
test junit
activityaware
async
rxjava
-->

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
