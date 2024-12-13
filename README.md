# sourcesync-android-example-minimal
A minimal example app that consists of only a player and loading a distribution.

# Getting started

1. Download and install [Android Studio](https://developer.android.com/studio)
2. Download this Apache 2 open source repo (i.e. ```gh repo clone Source-Digital/sourcesync-android-example-minimal```)
3. Open Android studio
4. Click the play button in the upper right. The application should start and you should see a demo distribution and video start playing!


# Next steps
Eveything is happening in ```\app\src\main\java\com\sourcedigital\sourcesync\myapplication\MainActivity.kt```

## Using your own key
A demo key is used by default so that anyone can see the example working, however, you should change this to your key if you have one, which will let you load your own customs settings. This is on line 39:

```kotlin
SourceSync.setup(context, "app.v1.demo")
```

## Using a different video
You can switch the video used with your own. This is on line 53:

```kotlin
val videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
```

## Using a different distribution
You can switch the distribution loaded with your own. This is on line 64:

```kotlin
val distribution = sourceSync.getDistribution("41508")
```

## Using different targets

The targeting and overlay system happens on lines 67-76, however, changing target locations and making custom overlays is outside the scope of this repo. Check out the [app example](https://github.com/Source-Digital/sourcesync-android-example), which includes a full app, a different player (the leanback player), multiple distributions and custom layouts to explore further examples.