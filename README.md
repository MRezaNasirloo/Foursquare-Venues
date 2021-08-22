# Foursquare Restaurants App

This project uses Foursquare Api to show restaurants on a map.

## Usage

To build the project, follow the steps below:

1. Provide a Google Map API key in the project's root `local.propertise` file. The steps are
   described [here](https://developers.google.com/maps/documentation/android/start#get-key)
   ```properties
   MAPS_API_KEY=YOUR_API_KEY
   ```
2. `./gradlew assembleDebug`

## Unit Test

To run the unit tests:

```
./gradlew test
```

## Architecture

This project follows MVVM architecture guidelines using Android Architecture Components. It's a
modular project consisting of 5 modules, a brief explanation of each module is provided below:

### App

This module is the application module. The core responsibility of this module is to set up and start
the application. Only two classes reside in this module:

* `App`
* `MainActivity`

The `MainActivity` class hosts a `FragmentContainerView` which uses a navigation graph to show
different
`Fragments`.

### Core

This module provides core classes and interfaces that are required by other modules and features.

### Navigation

There are a few ways to handle navigation in a modular structure, and each one of them has its pros
and cons. This project uses a bottom-level navigation graph; since it is more trivial and easier to
grasp.

### Venues Map

This module shows a map using Google Map SDK. The main class in this module is MapFragment which
hosts a `SupportMapFragment`. Adding new features is possible by extending the `MapPlugin`
interface.

### Venue Details

This module shows the restaurant's details on a simple page.
