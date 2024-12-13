package com.sourcedigital.sourcesync.myapplication

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.sourcedigital.sourcesync.myapplication.ui.theme.MyApplicationTheme
import android.widget.FrameLayout
import android.widget.VideoView
import io.sourcesync.android.SourceSync
import io.sourcesync.android.Distribution

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalTvMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          shape = RectangleShape
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current

            // Remember SourceSync instance
            val sourceSync = remember {
              SourceSync.setup(context, "app.v1.demo")
            }

            // Create video view and overlay container
            AndroidView(
              factory = { context ->
                FrameLayout(context).apply {
                  // Add VideoView with media controls
                  val videoView = VideoView(context)
                  val mediaController = MediaController(context)
                  mediaController.setAnchorView(videoView)
                  videoView.setMediaController(mediaController)

                  // Set video source - using a sample video URL
                  val videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                  videoView.setVideoURI(videoUri)

                  addView(videoView, FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                  ))

                  // Load distribution and create overlays
                  try {
                    // Using a demo distribution ID
                    val distribution = sourceSync.getDistribution("41508")

                    // Create overlays for all positions
                    val overlays = sourceSync.createPositionedOverlays(
                      distribution,
                      videoView,
                      "top", "bottom", "left", "right"
                    )

                    // Add overlays to the layout
                    overlays.forEach { (position, view) ->
                      addView(view)
                    }

                    // Start playing the video
                    videoView.start()
                  } catch (e: Exception) {
                    e.printStackTrace()
                  }
                }
              },
              modifier = Modifier.fillMaxSize()
            )

            // Cleanup when the composable is disposed
            DisposableEffect(Unit) {
              onDispose {
                // Add any cleanup code here if needed
              }
            }
          }
        }
      }
    }
  }

  override fun onBackPressed() {
    // Handle back button for detail view
    val sourceSync = SourceSync.setup(this, "app.v1.demo")
    if (!sourceSync.handleBackButton()) {
      super.onBackPressed()
    }
  }
}
