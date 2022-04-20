package pj.tdk.musicplayer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import pj.tdk.musicplayer.adapters.SongsAdapter
import pj.tdk.musicplayer.databinding.ActivityMainBinding
import pj.tdk.musicplayer.objects.SongList
import android.view.View
import androidx.core.content.ContentProviderCompat

import androidx.recyclerview.widget.RecyclerView

import androidx.core.content.ContentProviderCompat.requireContext




class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding? = null

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
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}