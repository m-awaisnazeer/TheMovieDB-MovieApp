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
