package pj.tdk.musicplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pj.tdk.musicplayer.db.blacklist.BlackListStoreDao
import pj.tdk.musicplayer.db.blacklist.BlackListStoreEntity
import pj.tdk.musicplayer.db.history.HistoryDao
import pj.tdk.musicplayer.db.history.HistoryEntity
import pj.tdk.musicplayer.db.lyrics.LyricsDao
import pj.tdk.musicplayer.db.lyrics.LyricsEntity
import pj.tdk.musicplayer.db.playcount.PlayCountDao
import pj.tdk.musicplayer.db.playcount.PlayCountEntity
import pj.tdk.musicplayer.db.playlist.PlaylistDao
import pj.tdk.musicplayer.db.playlist.PlaylistEntity
import pj.tdk.musicplayer.db.song.SongEntity

@Database(
    entities = [PlaylistEntity::class, SongEntity::class, HistoryEntity::class, PlayCountEntity::class, BlackListStoreEntity::class, LyricsEntity::class],
    version = 23,
    exportSchema = false
)

abstract class MainDB: RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun blackListStore(): BlackListStoreDao
    abstract fun playCountDao(): PlayCountDao
    abstract fun historyDao(): HistoryDao
    abstract fun lyricsDao(): LyricsDao
}