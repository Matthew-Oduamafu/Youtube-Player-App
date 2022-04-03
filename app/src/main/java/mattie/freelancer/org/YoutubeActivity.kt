package mattie.freelancer.org

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import java.util.concurrent.TimeUnit

const val YOUTUBE_VIDEO_ID = "URiDdlAS6ls"
const val YOUTUBE_PLAYLIST = "PLJYyuMTDI-idzy2eKxq16k5_RKD3odJ-a"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YoutubeActivity"
    private val DIALOG_REQUEST_CODE = 1

    val playerView by lazy{ YouTubePlayerView(this) } // pass in the context to it


    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_youtube)

        //*************************************************************************
        /*
        * Adding widgets to layout dynamically
        * we first call the layout here
        */
//        val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)
        // alternative we is to comment out the setContentView() line and the line above then do the below
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

        // adding button to layout in code
//        val button1 = Button(this)
//        button1.layoutParams = ConstraintLayout.LayoutParams(600, 800)
//        button1.text =  "Button Added"
//        layout.addView(button1)
        //**************************************
        // adding the playerView now
        // the line below has been re declared above to make it global to the class
//        val playerView = YouTubePlayerView(this)  // pass in the context to it
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)
        //**************************************

        //*************************************
        // initializing the google API
        playerView.initialize(
            getString(R.string.GOOGLE_API_KEY),
            this
        ) // pass in the context as well

    }

    /**Implementing the abstract members in ouTubePlayer.OnInitializedListener **/
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitializationSuccess: called provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: called youtubePlayer is ${youtubePlayer?.javaClass}")
        Toast.makeText(this, "Initialized Youtube Player successfully", Toast.LENGTH_SHORT).show()

        /**
         * Now we add the the event listeners in here
         * to make the class recognize and use it
         * **/
        youtubePlayer?.setPlaybackEventListener(playbackEventListener)
        youtubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)

        if (!wasRestored) {
            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
            youtubePlayer?.setFullscreen(false)
        }else{
            youtubePlayer?.play()
            youtubePlayer?.setFullscreen(false)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        if (youTubeInitializationResult != null) {
            if (youTubeInitializationResult.isUserRecoverableError) {
                youTubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
            } else {
                val errorMessage =
                    "There was an error initializing YoutubePlayer ($youTubeInitializationResult)"
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/


    /**Adding event playbackEventListener to the app
     * This is useful to show or do something when
     * the video is playing, paused, stopped buffering etc**/
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Video in play!!", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video Paused...", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video Stopped!", Toast.LENGTH_SHORT).show()
        }

        override fun onBuffering(p0: Boolean) {
            Toast.makeText(this@YoutubeActivity, "Buffering...", Toast.LENGTH_SHORT).show()
        }

        override fun onSeekTo(p0: Int) {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(p0.toLong())

            Log.d(TAG, "onSeekTo: Time in milli sec $p0")
            Log.d(TAG, "onSeekTo: Time in sec $minutes")
            val timeLeft = p0 - TimeUnit.MINUTES.toMillis(minutes)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeft)
            Log.d(TAG, "onSeekTo: Time left $timeLeft")
            Toast.makeText(
                this@YoutubeActivity, "Jumped to ${minutes}:${if (seconds<10) "0$seconds" else seconds}", Toast.LENGTH_SHORT
            ).show()
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/


    /***Adding the playerStateChangeListener to the app
     * This helps know when a video is loaded, ad is been
     * shown, video ends, video starts etc***/
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onLoading() {

        }

        override fun onLoaded(p0: String?) {

        }

        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Ad will end soon\uD83D\uDE0A", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onVideoStarted() {
            Toast.makeText(
                this@YoutubeActivity,
                "Enjoy this video\uD83D\uDE0D\uD83D\uDC95",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onVideoEnded() {
            Toast.makeText(
                this@YoutubeActivity,
                "Congrats\uD83C\uDF89\uD83E\uDD73 you've reached the end",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            Toast.makeText(this@YoutubeActivity, "Oops\uD83D\uDE31\uD83E\uDD74", Toast.LENGTH_SHORT)
                .show()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: called with response code $resultCode for request $requestCode")
        if (requestCode ==DIALOG_REQUEST_CODE){
            intent?.toString()?.let { Log.d(TAG, it) }
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this@YoutubeActivity)
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
}