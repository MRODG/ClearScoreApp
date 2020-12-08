# ClearScoreApp
A demo app that uses api calls to get data and rich animations to present them.

- Supported Android API versions: 26 and above
- Programming language: Kotlin
- Supported device types: Phone only
- Supported orientation: Portrait only

## Architecture
MVVM design pattern, great for its modularity and code separation and recommended by Google and has direct support in the platform.
For asynchronous service calls I developed my own simple callback interface to make api calls through Retrofit on the model side. Chose to write my own interface
over using big libraries like RXJava because all their functionality is not needed for this simple demo making my apk more lightweight. It also allows me to
manage my service requests and errors in my base Activity/ViewModel so I have full control.
Used LiveData for observing the viewmodels on the view side making it lifecycle aware of these components

## Dependency Injection
Dependency injection to reduce coupling and simplify testing.
For dependency injection I used Koin. A service locator framwerork that is much simpler and does not require
code generation and complex set up like dagger.

## Project Structure
I structure code by feature. This method makes the codebase modular. In this project currently just have the 'dashboardfeature' package

## Testing
Wrote unit tests covering all the scenarios in my VieModel and Service classes. Also wrote UI tests for the DashBoardActivity

## Some Notes
Used Moshi instead of GSON for handling the Json response from service calls. It has a greater upcoming kotlin support,
allows to easily write custom Json adapters if needed, allows predictable and better Json exception, JsonReader.selectName()
avoids unnecessary UTF-8 decoding, its more light weight and amongst others.

In Koin (dependency injection framework) Module.kt, I am able to easily resolve dependencies for our retrofit objects
which is injected to the DashboardService class. Used Moshi's ConverterFactory and Moshis default adapter KotlinJsonAdapterFactory collect fields
that we need from the response payload making our model data class lean.

## Build And Run
The easiest way to build/run application is to open project folder in Android Studio or Intelij with appropriate plug ins
Run the App module on a configured emulator

## Testing
To run tests go to appropriate test folders and run tests
I used the standard Mockito and Espresso testing framework. Also 'com.nhaarman.mockitokotlin2:mockito' a framework that provides useful
helper functions for working with Mockito in Kotlin


## Assumptions
Looking at the response from the endpoint provided I make use of the fields "score", "maxScoreValue" and "scoreBand", to build my view for the dashboard Activity. Looking at article
https://www.clearscore.com/learn/credit-score-and-report/what-is-a-good/bad-credit-score/ I found that there is 5 score bands at ClearScore so this allowed me to assume that scoreBand 0 is
the lowest and 4 the highest score and colour coordinating each  with appropriate colour that can be used in the View.

## Design Decisions
I chose to use one activity to show the score indicator view, which observes Livedata objects in the ViewModels to get data or error sources. The ViewModels implement a callback interface I designed
to make service calls in Service class (MVVM). Error sources and request progress indicators are managed in My BaseViewModel and BaseActivity class which most activities and viewmodels would extend
so all these background processes can be manged centrally.

Designed the a ScoreRating enum class that would hold reference for each Scoreband including Equifax band, ClearScore Name and score color making it easier to then populate score views with appropriate
colors and description text if ever needed.

To create the donut shaped score view, I had create a custom android view (ScoreIndicatorView) with an embedded animator to dynamically extend the  score indicator arch for greater user experience. This custom view get
updated by the service calls with the correct numbers and colour. The length of the score arch is a fraction calculated from the ration between the "score" and "maxScoreValue" for the endpoint response.

Whilst I was in a animation creating mode I decided to add another custom view (ProgressButton) which acts as a refresh button. Which allows the user to refresh the data in case the endpoint failed to return data or if there
was a network outage. This custom view Animates between a Button and a progress wheel when the app in a normal or requesting state. Used lottie framework to hold the json animation. for the progress wheel.


## Demo

Successful run
<img src="/demo/clearscorepassdemo.gif" width=250 height=500>

Fail run
<img src="demo/clearscorephoneofflinedemo.gif" width=250 height=500>

- note in this demo I added a handler delay to emulate a proper/slower service call to see the button animation properly


