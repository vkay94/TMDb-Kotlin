# TMDb-Kotlin

Hello folks, this is the documentation page for the TMDb wrapper _TMDb-Kotlin_ is written in and for 
Kotlin. I've created the library for Android apps primarily but it can be also used in any
Gradle project.

Most of the functionality and callbacks are implemented (but not all). The library is therefore WIP, so there
might be breaking changes in the future (but I hope not).

If you've got questions, suggestions or encounter any issues feel free to contact me via GitHub
(link to the upper right). I'd be happy to help ;)

## Setup

### Import into your project

This library uses the [JitPack.io][link_jitpack] so you've to dd it to your projects gradle file:

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

Then, on app level add the library:

```gradle
dependencies {
    implementation "com.github.vkay94:TMDb-Kotlin:xyz"
    
    // This module uses the ThreetenABP library to ensure LocalDate support in 
    // Android versions below API 26 so you should use this as well for 
    // using dates.
    implementation "com.jakewharton.threetenabp:threetenabp:1.3.0"
}
```

### Initialize the instance

Basic initialization:

```kotlin
TMDb.init(API_KEY [, V4_AUTH_KEY]) // [] = optional
```

In Android applications it is best-practice to initialize the instance directly within `App` to make
use of Kotlin's objects, so the instance can be used in the whole app directly.

=== "App.kt"

    ```kotlin
    class App : Application() {

        override fun onCreate() {
            TMDb.init(API_KEY)
            // or TMDb.init(YOUR_API_KEY, V4_AUTH_KEY) for authentication
        }
    }
    ```

=== "AndroidManifest.xml"

    ```xml
        <manifest
            ...> 
            <uses-permission android:name="android.permission.INTERNET" />

            <application
                android:name=".App"
                ...>
            </application>
        </manifest>
    ```

## Making requests

In the moment all calls are suspended and require to be called in Kotlin Coroutines. An overview
of how to use them can be found here:
[Composing suspending functions][kotlin_suspended].

They're also wrapped in `NetworkResponse`s so you can react for errors easily or access the
data directly.

### Examples

#### Searching with error handling

There are four cases which the response can take value of:
`Success`, `ServerError`, `NetworkError` and `UnknownError`.
You should take a look [here][link_networkresponse_types] for more info.

```kotlin
fun main() = runBlocking {
    TMDb.init("YOUR_API_KEY")

    when (val response = TMDb.searchService.tv("Black Clover")) {
        // Type: NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>
        is NetworkResponse.Success -> {
            val searchPage: TmdbPage<TmdbShow.Slim> = response.body
            if (searchPage.totalResults > 0) {
                println("First result's title: ${searchPage.results[0].title}")
            }
        }
        is NetworkResponse.ServerError -> {
            val errorBody: TmdbErrorResponse? = response.body
            println("ServerError: Message = ${errorBody?.message}")
        }
        is NetworkResponse.NetworkError -> {
            val error: IOException = response.error
            println("NetworkError: Message = ${error.message}")
        }
        is NetworkResponse.UnknownError -> {
            val error: Throwable = response.error
            println("UnknownError: Message = ${error.message}")
        }
    }
}
```

#### Searching with direct access

You can also access the response directly without checking for the NetworkResponse type by calling `invoke()`.
In this case the object might by `null`:

```kotlin
fun main() = runBlocking {
    TMDb.init("YOUR_API_KEY")

    val searchPage: TmdbPage<TmdbShow.Slim>? = 
        TMDb.searchService.tv("Black Clover").invoke()
        
    searchPage?.let {
        if (searchPage.totalResults > 0) {
                println("First result: ${searchPage.results[0].title}")
        }
    }
}
```

## Next steps

This introduction is pretty briefly. For some more practice scenarios you can either look into the
Recipes page on the left or the tests folder on Github [here][github_tests].

## Thanks

Thanks go to

- _TMDb-Java_ by xyz for the basic idea, especially the service accessing in the object instance
- _NetworkResponse library_ by xyz for the great error handling
- _Moshi_ by Square for custom JSON deserializing to make the useage more natural

<!-- Links -->
[kotlin_suspended]: https://google.com
[link_networkresponse_types]: https://google.com
[github_tests]: abc
[link_jitpack]: abc