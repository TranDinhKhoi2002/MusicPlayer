package pj.tdk.musicplayer.extensions

import pj.tdk.musicplayer.models.Song
import pj.tdk.musicplayer.util.MusicUtil

val Song.uri get() = MusicUtil.getSongFileUri(songId = id)