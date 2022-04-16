package pj.tdk.musicplayer.models.smartplaylist

import kotlinx.parcelize.Parcelize
import pj.tdk.musicplayer.App
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.models.Song
import pj.tdk.musicplayer.models.smartplaylist.AbsSmartPlaylist

@Parcelize
class ShuffleAllPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.action_shuffle_all),
    iconRes = R.drawable.ic_shuffle
) {
    override fun songs(): List<Song> {
        return songRepository.songs()
    }
}