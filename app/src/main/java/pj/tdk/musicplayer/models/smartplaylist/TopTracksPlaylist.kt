package pj.tdk.musicplayer.models.smartplaylist

import kotlinx.parcelize.Parcelize
import pj.tdk.musicplayer.App
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.models.Song
import pj.tdk.musicplayer.models.smartplaylist.AbsSmartPlaylist

@Parcelize
class TopTracksPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.my_top_tracks),
    iconRes = R.drawable.ic_trending_up
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.topTracks()
    }
}