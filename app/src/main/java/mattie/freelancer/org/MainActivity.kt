package mattie.freelancer.org

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // adding the onClickListener
        btnPlaySingle.setOnClickListener(this)
        btnStandalone.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.btnPlaySingle -> {
                Log.d(TAG, "\n\nonClick: Single button clicked\n\n\n")
                Intent(this, YoutubeActivity::class.java)
            }
            R.id.btnStandalone -> {
                Log.d(TAG, "\n\nonClick: Standalone button clicked\n\n\n")
                Intent(this, StandaloneActivity::class.java)
            }

            else -> throw IllegalArgumentException("Undefined button clicked")
        }
        startActivity(intent)
    }
}