package pj.tdk.musicplayer.models.smartplaylist

import kotlinx.parcelize.Parcelize
import pj.tdk.musicplayer.App
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.models.Song
import pj.tdk.musicplayer.models.smartplaylist.AbsSmartPlaylist

@Parcelize
class NotPlayedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.not_recently_played),
    iconRes = R.drawable.ic_watch_later
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.notRecentlyPlayedTracks()
    }
}