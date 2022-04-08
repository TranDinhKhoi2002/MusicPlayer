package pj.tdk.musicplayer.interfaces

import pj.tdk.musicplayer.models.Album
import pj.tdk.musicplayer.models.Artist
import pj.tdk.musicplayer.models.Genre

interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}