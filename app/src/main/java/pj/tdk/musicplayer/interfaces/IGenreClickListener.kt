package pj.tdk.musicplayer.interfaces

import android.view.View
import pj.tdk.musicplayer.models.Genre

interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}