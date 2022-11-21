package com.yusufcakmak.exoplayersample

import android.app.Activity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.Util
import com.yusufcakmak.exoplayersample.databinding.ActivityVideoPlayerBinding


class VideoPlayerActivity : Activity() {

    private lateinit var simpleExoPlayer: ExoPlayer
    private lateinit var binding: ActivityVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializePlayer() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(STREAM_URL))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer.addMediaSource(mediaSource)

        simpleExoPlayer.volume = 0f
        binding.playerView.player = simpleExoPlayer
        binding.playerView.requestFocus()
        simpleExoPlayer.repeatMode = Player.REPEAT_MODE_ALL;
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.prepare()

    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

    companion object {
        const val STREAM_URL = "http://docs.evostream.com/sample_content/assets/bun33s.mp4"

        //const val STREAM_URL = "https://pbn.deliveryclouds.com/user/posts/post_img_1668764205605.mp4"
        //const val STREAM_URL = "http://docs.evostream.com/sample_content/assets/bun33s.mp4"
       // const val STREAM_URL = "https://pbn.deliveryclouds.com/user/posts/post_img_1668769909176.mp4"
        //const val STREAM_URL = "https://pbn.deliveryclouds.com/user/posts/post_img_1668770008881.mp4"
    }
}