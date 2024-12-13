// Package declaration - defines where this code lives in the project structure
package com.sourcedigital.sourcesync.myapplication

// Import statements - tell Kotlin which external classes and functions we want to use
import android.net.Uri                                     // For handling URLs/URIs
import android.os.Bundle                                   // For passing data between components
import android.widget.MediaController                      // Adds play/pause controls to video
import androidx.activity.ComponentActivity                 // Base class for activities
import androidx.activity.compose.setContent                // For setting Compose content
import androidx.compose.foundation.layout.Box              // A Compose layout container
import androidx.compose.foundation.layout.fillMaxSize      // Modifier to fill available space
import androidx.compose.runtime.DisposableEffect           // For cleanup when UI components are removed
import androidx.compose.ui.Modifier                        // For modifying UI components
import androidx.compose.ui.graphics.RectangleShape         // Basic rectangle shape
import androidx.compose.ui.platform.LocalContext           // For accessing Android context
import androidx.compose.ui.viewinterop.AndroidView         // For using traditional Android views in Compose
import androidx.tv.material3.ExperimentalTvMaterial3Api    // Marks TV-specific Material Design API
import androidx.tv.material3.Surface                       // Material Design container
import com.sourcedigital.sourcesync.myapplication.ui.theme.MyApplicationTheme  // App's theme
import android.widget.FrameLayout                          // Android container view
import android.widget.VideoView                            // Android video player view
import io.sourcesync.android.SourceSync                    // SourceSync SDK

// MainActivity is the main screen of our app
// ComponentActivity is the base class for modern Android activities
class MainActivity : ComponentActivity() {
    // lateinit tells Kotlin we'll initialize this variable later
    // This stores our SourceSync instance that we'll use throughout the activity
    private lateinit var sourceSync: SourceSync

    // onCreate is called when the activity is first created
    // The @OptIn annotation tells Kotlin we know we're using experimental TV features
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the parent class's onCreate first
        super.onCreate(savedInstanceState)
        
        // Initialize SourceSync with our API key
        sourceSync = SourceSync.setup(this, "app.v1.demo")
        
        // setContent is used to set the UI content using Jetpack Compose
        setContent {
            // Apply our app's theme to all content
            MyApplicationTheme {
                // Surface is a Material Design container that can have elevation and background color
                Surface(
                    // Modifier.fillMaxSize() makes the surface fill the whole screen
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    // Box is a simple layout container that can stack its children
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Get the current Android context for creating views
                        val context = LocalContext.current

                        // AndroidView lets us use traditional Android views in Compose
                        AndroidView(
                            // factory is a function that creates our view hierarchy
                            factory = { _ ->
                                // Create a FrameLayout to hold our video and overlays
                                FrameLayout(context).apply {
                                    // Create the video player
                                    val videoView = VideoView(context)
                                    // Add media controls (play/pause/seek)
                                    val mediaController = MediaController(context)
                                    mediaController.setAnchorView(videoView)
                                    videoView.setMediaController(mediaController)

                                    // Set the video source URL
                                    val videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                                    videoView.setVideoURI(videoUri)

                                    // Add the video view to our layout, making it fill the container
                                    addView(videoView, FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT,
                                        FrameLayout.LayoutParams.MATCH_PARENT
                                    ))

                                    try {
                                        // Load the SourceSync distribution (content configuration)
                                        val distribution = this@MainActivity.sourceSync.getDistribution("41508")
                                        // Create overlay views for all positions
                                        val overlays = this@MainActivity.sourceSync.createPositionedOverlays(
                                            distribution,
                                            videoView,
                                            "top", "bottom", "left", "right"
                                        )

                                        // Add all overlay views to our layout
                                        overlays.forEach { (_, view) ->
                                            addView(view)
                                        }

                                        // Start playing the video
                                        videoView.start()
                                    } catch (e: Exception) {
                                        // Print any errors to the debug log
                                        e.printStackTrace()
                                    }
                                }
                            },
                            // Make the AndroidView fill the available space
                            modifier = Modifier.fillMaxSize()
                        )

                        // DisposableEffect runs cleanup code when the composable is disposed
                        DisposableEffect(Unit) {
                            onDispose {
                                // Cleanup code would go here
                                // (currently empty but could be used for cleanup tasks)
                            }
                        }
                    }
                }
            }
        }
    }

    // Handle back button presses
    override fun onBackPressed() {
        // Let SourceSync handle the back press first (for closing detail views)
        // If it doesn't handle it (returns false), call the default back behavior
        if (!sourceSync.handleBackButton()) {
            super.onBackPressed()
        }
    }
}