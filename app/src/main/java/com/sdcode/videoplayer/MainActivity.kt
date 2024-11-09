package com.sdcode.videoplayer

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private val REQUEST_CODE_SELECT_VIDEO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        val selectVideoButton: Button = findViewById(R.id.selectVideoButton)

        selectVideoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "video/*"
            }
            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO)
        }

        playButton.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.start()
            }
        }

        pauseButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_VIDEO && resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = data?.data
            videoUri?.let {
                videoView.setVideoURI(it)
                videoView.start()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (videoView.isPlaying) {
            videoView.pause() // Pause video if app goes to the background
        }
    }
}