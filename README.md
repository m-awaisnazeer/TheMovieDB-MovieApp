# TheMovieDB-MovieApp

TheMovieDB-MovieApp - App consuming a [themoviedb](https://developers.themoviedb.org/3/getting-started/introduction) to display Movies it has been built  on MVVM with Clean Architecture.


App features:

- List of Movies
- Details of Movie
- Favorite Movies
- Search Movies

# Architecture
Uses concepts of the notorious Uncle Bob's architecture called [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

- Better separation of concerns. Each module has a clear API., Feature related classes life in different modules and can't be referenced without explicit module dependency.
- Features can be developed in parallel eg. by different teams
- Each feature can be developed in isolation, independently from other features
- faster compile time


![image](https://user-images.githubusercontent.com/50645184/198883092-781c566c-558f-4187-896f-0d1365e4519a.png)

# Layers:
- data - The data layer implements the repository interface that the domain layer defines. This layer provide a single source of truth for data. (Kotlin module that can only access domain layer)
  - remote - Handles data interacting with the network. (can only access data layer)
  - cache - Handles data interacting with the local storing (Room DB). (can only access data layer)
- domain - The domain layer contains the UseCases that encapsulate a single and very specific task that can be performed. This task is part of the business logic of the application. (Kotlin module that cannot access any other layer)
- presentation - MVVM with ViewModels exposing LiveData or StateFlow that the UI consume. The ViewModel does not know anything about it's consumers. (Android module that can only access domain layer)

# Modularization

Modularization is the practice of breaking the concept of a monolithic, one-module codebase into
loosely coupled, self contained modules.


### Benefits of modularization

This offers many benefits, including:

**Scalability** - In a tightly coupled codebase, a single change can trigger a cascade of
alterations. A properly modularized project will embrace
the [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns) principle. This
in turn empowers the contributors with more autonomy while also enforcing architectural patterns.

**Enabling work in parallel** - Modularization helps decrease version control conflicts and enables
more efficient work in parallel for developers in larger teams.

**Ownership** - A module can have a dedicated owner who is responsible for maintaining the code and
tests, fixing bugs, and reviewing changes.

**Encapsulation** - Isolated code is easier to read, understand, test and maintain.

**Reduced build time** - Leveraging Gradle’s parallel and incremental build can reduce build times.

**Dynamic delivery** - Modularization is a requirement
for [Play Feature Delivery](https://developer.android.com/guide/playcore/feature-delivery) which
allows certain features of your app to be delivered conditionally or downloaded on demand.

**Reusability** - Proper modularization enables opportunities for code sharing and building multiple
apps, across different platforms, from the same foundation.


### Modularization pitfalls

However, modularization is a pattern that can be misused, and there are some gotchas to be aware of
when modularizing an app:

**Too many modules** - each module has an overhead that comes in the form of increased complexity of
the build configuration. This can cause Gradle sync times to increase, and incurs an ongoing
maintenance cost. In addition, adding more modules increases the complexity of the project’s Gradle
setup, when compared to a single monolithic module. This can be mitigated by making use of
convention plugins, to extract reusable and composable build configuration into type-safe Kotlin
code. In the Now in Android app, these convention plugins can be found in
the [`build-logic` folder](https://github.com/android/nowinandroid/tree/main/build-logic).

**Not enough modules** - conversely if your modules are few, large and tightly coupled, you end up
with yet another monolith. This means you lose some benefits of modularization. If your module is
bloated and has no single, well defined purpose, you should consider splitting it.

**Too complex** - there is no silver bullet here. In fact it doesn’t always make sense to modularize
your project. A dominating factor is the size and relative complexity of the codebase. If your
project is not expected to grow beyond a certain threshold, the scalability and build time gains
won’t apply.


## Modularization strategy

It’s important to note that there is no single modularization strategy that fits all projects.
However, there are general guidelines that can be followed to ensure you maximize its benefits and
minimize its downsides.

A barebone module is simply a directory with a Gradle build script inside. Usually though, a module
will consist of one or more source sets and possibly a collection of resources or assets. Modules
can be built and tested independently. Due to Gradle's flexibility there are few constraints as to
how you can organize your project. In general, you should strive for low coupling and high cohesion.

* **Low coupling** - Modules should be as independent as possible from one another, so that changes
  to one module have zero or minimal impact on other modules. They should not possess knowledge of
  the inner workings of other modules.

* **High cohesion** - A module should comprise a collection of code that acts as a system. It should
  have clearly defined responsibilities and stay within boundaries of certain domain knowledge. For
  example,
  the [`core:network` module](https://github.com/m-awaisnazeer/TheMovieDB-MovieApp/tree/master/core/network) in Movie App is responsible for making network requests, handling responses from a remote data
  source, and supplying data to other modules.


## Types of modules in Movie App

The Movie app contains the following types of modules:

* The `app` module - contains app level and scaffolding classes that bind the rest of the codebase,
  such as `MainActivity`, `MovieAapp` and app-level controlled navigation.

* `feature:` modules - feature specific modules which are scoped to handle a single responsibility
  in the app. These modules can be reused by any app, including test or other flavoured apps, when
  needed, while still keeping it separated and isolated. If a class is needed only by one `feature`
  module, it should remain within that module. If not, it should be extracted into an
  appropriate `core` module. A `feature` module should have no dependencies on other feature
  modules. They only depend on the `core` modules that they require.

* `core:` modules - common library modules containing auxiliary code and specific dependencies that
  need to be shared between other modules in the app. These modules can depend on other core
  modules, but they shouldn’t depend on feature nor app modules.


## Modules

Using the above modularization strategy, the Movie app has the following modules:

<table>
  <tr>
   <td><strong>Name</strong>
   </td>
   <td><strong>Responsibilities</strong>
   </td>
   <td><strong>Key classes and good examples</strong>
   </td>
  </tr>
  <tr>
   <td><code>app</code>
   </td>
   <td>Brings everything together required for the app to function correctly. 
   </td>
   <td><code>MovieApp, MainActivity</code><br>
   App-level controlled navigation via <code>res/navigation/mobile_navigation.xml</code>
   </td>
  </tr>
  <tr>
   <td><code>feature:1,</code><br>
   <code>feature:2</code><br>
   ...
   </td>
   <td>Functionality associated with a specific feature or user journey. Typically contains UI components and ViewModels which read data from other modules.<br>
   Examples include:<br>
   <ul>
      <li><a href="https://github.com/m-awaisnazeer/TheMovieDB-MovieApp/tree/master/feature/home"><code>feature:home</code></a> displays list of movies on HomeFragment</li>
      <li><a href="https://github.com/m-awaisnazeer/TheMovieDB-MovieApp/tree/master/feature/favorite"><code>feature:favorite</code></a> which displays list of favorites movies on FavoritesFragment</li>
      </ul>
   </td>
   <td><code>HomeFragment</code><br>
   <code>HomeViewModel</code><br>
   <code>FavoritesFragment</code><br>
   <code>FavoritesViewModel</code>
   </td>
  </tr>
  <tr>
   <td><code>core:data</code>
   </td>
   <td>Fetching app data from multiple sources, shared by different features.
   </td>
   <td><code>MovieRepositoryImp, DataModule</code><br>
   </td>
  </tr>
  <tr>
   <td><code>core:ui</code>
   </td>
   <td>UI components, Adapters and resources, such as icons, used by different features.
   </td>
   <td><code>FavoriteMoviesAdapter</code><br>
   <code>MovieAdapter</code><br>
   <code>SquareImageView</code>
   </td>
  </tr>
  <tr>
   <td><code>core:common</code>
   </td>
   <td>Common classes shared between modules.
   </td>
   <td><code>MovieRemoteMediator</code><br>
   <code>LoaderAdapter</code>
   </td>
  </tr>
  <tr>
   <td><code>core:network</code>
   </td>
   <td>Making network requests and handling responses from a remote data source.
   </td>
   <td><code>TheMovieDbApi</code>
   </td>
  </tr>
  <tr>
   <td><code>core:database</code>
   </td>
   <td>Local database storage using Room.
   </td>
   <td><code>MovieDatabase</code><br>
   <code>MovieDao, RemoteKeysDao</code><br>
   <code>DatabaseModule</code> classes
   </td>
  </tr>
</table>


# Tech stack - Library:
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) - Flow is used to pass (send) a stream of data that can be computed asynchronously
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for dependency injection.
- [Turbine](https://github.com/cashapp/turbine) Turbine is a small testing library for kotlinx.coroutines Flow.
- [JUnit](https://junit.org/junit5/) JUnit is a simple framework to write repeatable tests.
- [Truth](https://truth.dev/) Truth is a library for performing assertions in tests.
- [Robolectric](https://robolectric.org/) Robolectric is a framework that brings fast and reliable unit tests to Android. Tests run inside the JVM on your workstation in seconds.
- JetPack
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used get lifecyle event of an activity or fragment and performs some action in response to change
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Room](https://developer.android.com/training/data-storage/room) - Used to create room db and store the data.
  - [Pagging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Used to create room db and store the data.
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used to navigate between fragments
 - [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
 - [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
 - [Gson](https://github.com/google/gson) - Used to convert Java Objects into their JSON representation and vice versa.
 - [Glide](https://bumptech.github.io/glide/) - Glide is a fast and efficient image loading library for Android

# TODO
- [ ] Unit tests
- [ ] Jacoco for test coverage

# References:
 - Book: [Real-World Android by Tutorials](https://www.kodeco.com/books/real-world-android-by-tutorials)
 - Guide: [Guide to app architecture.](https://developer.android.com/topic/architecture/intro)
 - Repository: This repository code is mostly inspired by [Real-World Android by Tutorials](https://github.com/kodecocodes/adva-materials) github Repository.
