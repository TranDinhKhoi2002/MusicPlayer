package pj.tdk.musicplayer.models.smartplaylist

import androidx.annotation.DrawableRes
import pj.tdk.musicplayer.models.smartplaylist.PlaylistIdGenerator
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.models.AbsCustomPlaylist

abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)