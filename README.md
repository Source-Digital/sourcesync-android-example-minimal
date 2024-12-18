# Minimal example application using SourceSync's native SDK
A minimal example app that consists of only a player and loading a distribution.

# Getting started

1. Download and install [Android Studio](https://developer.android.com/studio)
2. Download this Apache 2 open source repo (i.e. ```gh repo clone Source-Digital/sourcesync-android-example-minimal```)
3. Open Android studio
4. Click the play/reload button in the upper right. The application should start and you should see a demo distribution and video start playing!

![image](https://github.com/user-attachments/assets/9a21df0b-a8a8-413e-bde7-a7327e390433)


# Next steps
Eveything is happening in ```\app\src\main\java\com\sourcedigital\sourcesync\myapplication\MainActivity.kt```

## Using your own key
A demo key is used by default so that anyone can see the example working, however, you should change this to your key if you have one, which will let you load your own customs settings. This is on line 39:

```kotlin
SourceSync.setup(context, "app.v1.demo")
```

## Using a different video
You can switch the video used with your own. This is on line 70:

```kotlin
val videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
```

## Using a different distribution
You can switch the distribution loaded with your own. This is on line 81:

```kotlin
val distribution = sourceSync.getDistribution("41508")
```

## See everything that is going on
SourceSync's native SDK logs tons of details about what is going on. Check them to see details as to what's happening.

![image](https://github.com/user-attachments/assets/726c0bf5-9e55-49ba-ade1-eb696ca9a1b0)

You can filter by component type as well. At the top of Logcat, you can type "SourceSync." to filter only SourceSync activity. You can add "SourceSync.activation" to see only activation activity, and so on.

## Next steps

The targeting and overlay system happens on lines 67-76, however, changing target locations and making custom overlays is outside the scope of this repo. Check out the [app example](https://github.com/Source-Digital/sourcesync-android-example), which includes a full app, a different player (the leanback player), multiple distributions and custom layouts to explore further examples.
