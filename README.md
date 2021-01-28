E-Lesson
===========================================================

This is a full fledge e-learning app that uses Android Architecture Components
with Dagger 2, Retrofit and ExoPlayer.

Introduction
-------------

### Functionality
The app is composed of 3 main screens.

#### HomeFragment
This consist of two segment the first segment comes with grids of subject
items available, where data fetched from the network are  kept in the database
using room and fetched appropriately to corresponding grids while the second
displays the recently watched topic video thumbnails with title and description

#### LessonsFragment
This fragment displays the lessons oof each subject in a horizontal scrolling
recyclerview categorized and grouped by their chapters

#### PlayerFragment
This fragment displays the media content of each lessons using exoplayer for video playbacks
and lesson title with corresponding chapter name displayed

### Building
You can open the project in Android studio and press run.

### Architectural Decisions
The architectural design is a Model View and ViewModel(MVVM) approach with separation of concerns
in the repository where the data source of truth are decided either to make a network call or fetch
from the internal database.

This approach is made possible using Dagger dependency injection to make object interaction
across the codebase seamless.

Not leaving out the Android Architecture Components like data binding, navigation amongst
others that make the development process less error prone with drastic reductions of boiler plate
codes

Retrofit which serves as the networking library inter play well with room database using live data
full integration to continually serve data

### Testing
The project uses both instrumentation tests that run on the device
and local unit tests that run on your computer.
To run both of them and generate a coverage report, you can run:

`./gradlew fullCoverageReport` (requires a connected device or an emulator)

#### Device Tests
##### UI Tests
The projects uses Espresso for UI testing. Since each fragment
is limited to a ViewModel, each test mocks related ViewModel to
run the tests.
##### Database Tests
The project creates an in memory database for each database test but still
runs them on the device.

#### Local Unit Tests
##### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository
implementations.
##### Repository Tests
Each Repository is tested using local unit tests with mock web service and
mock database.
##### Webservice Tests
The project uses [MockWebServer][mockwebserver] project to test REST api interactions.


### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Exoplayer][exoplayer] application level media player
* [Timber][timber] for logging
* [espresso][espresso] for UI tests
* [mockito][mockito] for mocking in tests


[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[espresso]: https://google.github.io/android-testing-support-library/docs/espresso/
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[exoplayer]: https://exoplayer.dev/
[timber]: https://github.com/JakeWharton/timber
[mockito]: http://site.mockito.org

License
--------

