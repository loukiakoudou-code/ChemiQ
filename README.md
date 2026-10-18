# ChemiQ

ChemiQ is an Android quiz app for practising chemistry. Pick a game, choose a unit and a difficulty, and answer the questions.

> 🚧 Work in progress. Some screens are not built yet.

## Features

- Home screen with the available games
- Pick a unit and a difficulty level
- Answer quiz questions
- Planned: results, review, progress, settings, periodic table, equation balancing and matching games

## Tech stack

- Kotlin 2.2
- Jetpack Compose with Material 3
- Navigation Compose with type-safe routes (kotlinx.serialization)
- Hilt for dependency injection
- MVVM, where ViewModels expose immutable screen state built by providers

## Requirements

- Android Studio (latest stable)
- JDK 17 or newer
- Android 7.0 (API 24) or newer

## Getting started

```bash
git clone https://github.com/loukiakoudou-code/ChemiQ.git
cd ChemiQ
./gradlew installDebug
```

You can also open the project in Android Studio and click **Run**.

## Project structure

```
app/src/main/java/com/lkountoura/chemiq/
├── di/          Hilt modules
├── navigation/  Nav host and routes
├── provider/    Builders for screen state
├── ui/          Screens, components, state and theme
└── viewmodel/   Screen ViewModels
```

## License

Licensed under the [Apache License 2.0](LICENSE).
