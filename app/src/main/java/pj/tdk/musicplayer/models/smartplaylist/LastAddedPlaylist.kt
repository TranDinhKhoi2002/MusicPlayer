package pj.tdk.musicplayer.models.smartplaylist

import kotlinx.parcelize.Parcelize
import pj.tdk.musicplayer.App
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.models.Song
import pj.tdk.musicplayer.models.smartplaylist.AbsSmartPlaylist

@Parcelize
class LastAddedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.last_added),
    iconRes = R.drawable.ic_library_add
) {
    override fun songs(): List<Song> {
        return lastAddedRepository.recentSongs()
    }
}