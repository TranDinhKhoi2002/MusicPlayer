package pj.tdk.musicplayer.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pj.tdk.musicplayer.databinding.ActivitySongViewBinding

class SongViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongViewBinding.inflate(layoutInflater)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            binding.txtSong.text = bundle.getString("song")
            binding.txtSinger.text = bundle.getString("singer")
        }

        setContentView(binding.root)

        binding.imgBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TODO("Not yet implemented")
                finish()
            }
        })
    }
}