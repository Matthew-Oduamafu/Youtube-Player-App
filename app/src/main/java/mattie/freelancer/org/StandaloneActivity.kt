package mattie.freelancer.org

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone.*

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "StandaloneActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standalone)

        btnPlayVideo.setOnClickListener(this)
        btnPlaylist.setOnClickListener(this)

        /**ALTERNATIVE WAYS FOR DOING THE TWO LINES OF CODE ABOVE**/
        /*
        // the first way
        btnPlayVideo.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                TODO("Not yet implemented")
            }
        })

        // the second way
        btnPlaylist.setOnClickListener(
            View.OnClickListener{ v ->
                TODO("Yet to implement")
            }
        )

        // another way of doing it is assigning the OnclickListener to a variable
        val listener = View.OnClickListener{
            TODO("To implement")
        }
        // the we pass it to the buttons that will need it
        btnPlaylist.setOnClickListener(listener)
        btnPlayVideo.setOnClickListener(listener)
        */
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.btnPlayVideo -> {
                /*
                YouTubeStandalonePlayer.createVideoIntent(
                    this,
                    getString(R.string.GOOGLE_API_KEY),
                    YOUTUBE_VIDEO_ID
                )
                 */
                YouTubeStandalonePlayer.createVideoIntent(
                    this,
                    getString(R.string.GOOGLE_API_KEY),
                    YOUTUBE_VIDEO_ID,
                    0,
                    true,
                    true
                )
            }

            R.id.btnPlaylist -> {/*YouTubeStandalonePlayer.createPlaylistIntent(
                this,
                getString(R.string.GOOGLE_API_KEY),
                YOUTUBE_PLAYLIST
            )*/
                Log.d(TAG, "onClick: Playlist intent created")
                YouTubeStandalonePlayer.createPlaylistIntent(
                    this,
                    getString(R.string.GOOGLE_API_KEY),
                    YOUTUBE_PLAYLIST,
                    0,
                    0,
                    true,
                    true
                )
            }

            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        // then we start the intent
        startActivity(intent)
    }
}