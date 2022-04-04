package pj.tdk.musicplayer.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pj.tdk.musicplayer.adapters.SongsAdapter
import pj.tdk.musicplayer.databinding.ActivityMainBinding
import pj.tdk.musicplayer.objects.SongList

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val adapter = SongsAdapter(SongList.songList)
        binding?.rvRecentSong?.adapter = adapter
        binding?.rvFavouriteSong?.adapter = adapter
        binding?.rvAlbumSong?.adapter = adapter

        val layoutRecentSongManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val layoutFavouriteManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val layoutAlbumManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvRecentSong?.layoutManager = layoutRecentSongManager
        binding?.rvFavouriteSong?.layoutManager = layoutFavouriteManager
        binding?.rvAlbumSong?.layoutManager = layoutAlbumManager

        binding?.ivMenuSettings?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TODO("Not yet implemented")
                val intent: Intent = Intent(view?.context, PlayListActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}