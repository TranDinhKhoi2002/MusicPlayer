package pj.tdk.musicplayer.models.smartplaylist

import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import pj.tdk.musicplayer.App
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.models.Song
import pj.tdk.musicplayer.models.smartplaylist.AbsSmartPlaylist

@Parcelize
class HistoryPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.history),
    iconRes = R.drawable.ic_history
), KoinComponent {

    override fun songs(): List<Song> {
        return topPlayedRepository.recentlyPlayedTracks()
    }
}