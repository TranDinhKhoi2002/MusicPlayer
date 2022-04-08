package pj.tdk.musicplayer.interfaces

import android.view.View
import pj.tdk.musicplayer.db.playlist.PlaylistWithSongs

interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}