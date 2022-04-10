package pj.tdk.musicplayer.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pj.tdk.musicplayer.db.*
import code.name.monkey.retromusic.helper.SortOrder.PlaylistSortOrder.Companion.PLAYLIST_A_Z
import code.name.monkey.retromusic.helper.SortOrder.PlaylistSortOrder.Companion.PLAYLIST_SONG_COUNT
import code.name.monkey.retromusic.helper.SortOrder.PlaylistSortOrder.Companion.PLAYLIST_SONG_COUNT_DESC
import code.name.monkey.retromusic.helper.SortOrder.PlaylistSortOrder.Companion.PLAYLIST_Z_A
import pj.tdk.musicplayer.util.PreferenceUtil
import pj.tdk.musicplayer.R
import pj.tdk.musicplayer.db.blacklist.BlackListStoreDao
import pj.tdk.musicplayer.db.blacklist.BlackListStoreEntity
import pj.tdk.musicplayer.db.history.HistoryDao
import pj.tdk.musicplayer.db.history.HistoryEntity
import pj.tdk.musicplayer.db.lyrics.LyricsDao
import pj.tdk.musicplayer.db.playcount.PlayCountDao
import pj.tdk.musicplayer.db.playcount.PlayCountEntity
import pj.tdk.musicplayer.db.playlist.PlaylistDao
import pj.tdk.musicplayer.db.playlist.PlaylistEntity
import pj.tdk.musicplayer.db.playlist.PlaylistWithSongs
import pj.tdk.musicplayer.db.song.SongEntity
import pj.tdk.musicplayer.db.song.toHistoryEntity
import pj.tdk.musicplayer.models.Song


interface RoomRepository {
    fun historySongs(): List<HistoryEntity>
    fun favoritePlaylistLiveData(favorite: String): LiveData<List<SongEntity>>
    fun insertBlacklistPath(blackListStoreEntity: BlackListStoreEntity)
    fun observableHistorySongs(): LiveData<List<HistoryEntity>>
    fun getSongs(playListId: Long): LiveData<List<SongEntity>>
    suspend fun createPlaylist(playlistEntity: PlaylistEntity): Long
    suspend fun checkPlaylistExists(playlistName: String): List<PlaylistEntity>
    suspend fun playlists(): List<PlaylistEntity>
    suspend fun playlistWithSongs(): List<PlaylistWithSongs>
    suspend fun insertSongs(songs: List<SongEntity>)
    suspend fun deletePlaylistEntities(playlistEntities: List<PlaylistEntity>)
    suspend fun renamePlaylistEntity(playlistId: Long, name: String)
    suspend fun deleteSongsInPlaylist(songs: List<SongEntity>)
    suspend fun deletePlaylistSongs(playlists: List<PlaylistEntity>)
    suspend fun favoritePlaylist(favorite: String): PlaylistEntity
    suspend fun isFavoriteSong(songEntity: SongEntity): List<SongEntity>
    suspend fun removeSongFromPlaylist(songEntity: SongEntity)
    suspend fun addSongToHistory(currentSong: Song)
    suspend fun songPresentInHistory(song: Song): HistoryEntity?
    suspend fun updateHistorySong(song: Song)
    suspend fun favoritePlaylistSongs(favorite: String): List<SongEntity>
    suspend fun insertSongInPlayCount(playCountEntity: PlayCountEntity)
    suspend fun updateSongInPlayCount(playCountEntity: PlayCountEntity)
    suspend fun deleteSongInPlayCount(playCountEntity: PlayCountEntity)
    suspend fun deleteSongInHistory(songId: Long)
    suspend fun clearSongHistory()
    suspend fun checkSongExistInPlayCount(songId: Long): List<PlayCountEntity>
    suspend fun playCountSongs(): List<PlayCountEntity>
    suspend fun insertBlacklistPath(blackListStoreEntities: List<BlackListStoreEntity>)
    suspend fun deleteBlacklistPath(blackListStoreEntity: BlackListStoreEntity)
    suspend fun clearBlacklist()
    suspend fun insertBlacklistPathAsync(blackListStoreEntity: BlackListStoreEntity)
    suspend fun blackListPaths(): List<BlackListStoreEntity>
    suspend fun deleteSongs(songs: List<Song>)
    suspend fun isSongFavorite(context: Context, songId: Long): Boolean
}

class RealRoomRepository(
    private val playlistDao: PlaylistDao,
    private val blackListStoreDao: BlackListStoreDao,
    private val playCountDao: PlayCountDao,
    private val historyDao: HistoryDao,
    private val lyricsDao: LyricsDao
) : RoomRepository {
    @WorkerThread
    override suspend fun createPlaylist(playlistEntity: PlaylistEntity): Long =
        playlistDao.createPlaylist(playlistEntity)

    @WorkerThread
    override suspend fun checkPlaylistExists(playlistName: String): List<PlaylistEntity> =
        playlistDao.playlist(playlistName)

    @WorkerThread
    override suspend fun playlists(): List<PlaylistEntity> = playlistDao.playlists()

    @WorkerThread
    override suspend fun playlistWithSongs(): List<PlaylistWithSongs> =
        when (PreferenceUtil.playlistSortOrder) {
            PLAYLIST_A_Z ->
                playlistDao.playlistsWithSongs().sortedBy {
                    it.playlistEntity.playlistName
                }
            PLAYLIST_Z_A -> playlistDao.playlistsWithSongs()
                .sortedByDescending {
                    it.playlistEntity.playlistName
                }
            PLAYLIST_SONG_COUNT -> playlistDao.playlistsWithSongs().sortedBy { it.songs.size }
            PLAYLIST_SONG_COUNT_DESC -> playlistDao.playlistsWithSongs()
                .sortedByDescending { it.songs.size }
            else -> playlistDao.playlistsWithSongs().sortedBy {
                it.playlistEntity.playlistName
            }
        }

    @WorkerThread
    override suspend fun insertSongs(songs: List<SongEntity>) {

        playlistDao.insertSongsToPlaylist(songs)
    }

    override fun getSongs(playListId: Long): LiveData<List<SongEntity>> =
        playlistDao.songsFromPlaylist(playListId)

    override suspend fun deletePlaylistEntities(playlistEntities: List<PlaylistEntity>) =
        playlistDao.deletePlaylists(playlistEntities)

    override suspend fun renamePlaylistEntity(playlistId: Long, name: String) =
        playlistDao.renamePlaylist(playlistId, name)

    override suspend fun deleteSongsInPlaylist(songs: List<SongEntity>) {
        songs.forEach {
            playlistDao.deleteSongFromPlaylist(it.playlistCreatorId, it.id)
        }
    }

    override suspend fun deletePlaylistSongs(playlists: List<PlaylistEntity>) =
        playlists.forEach {
            playlistDao.deletePlaylistSongs(it.playListId)
        }

    override suspend fun favoritePlaylist(favorite: String): PlaylistEntity {
        val playlist: PlaylistEntity? = playlistDao.playlist(favorite).firstOrNull()
        return if (playlist != null) {
            playlist
        } else {
            println("Playlist Created")
            createPlaylist(PlaylistEntity(playlistName = favorite))
            playlistDao.playlist(favorite).first()
        }
    }

    override suspend fun isFavoriteSong(songEntity: SongEntity): List<SongEntity> =
        playlistDao.isSongExistsInPlaylist(
            songEntity.playlistCreatorId,
            songEntity.id
        )

    override suspend fun removeSongFromPlaylist(songEntity: SongEntity) =
        playlistDao.deleteSongFromPlaylist(songEntity.playlistCreatorId, songEntity.id)

    override suspend fun addSongToHistory(currentSong: Song) =
        historyDao.insertSongInHistory(currentSong.toHistoryEntity(System.currentTimeMillis()))

    override suspend fun songPresentInHistory(song: Song): HistoryEntity? =
        historyDao.isSongPresentInHistory(song.id)

    override suspend fun updateHistorySong(song: Song) =
        historyDao.updateHistorySong(song.toHistoryEntity(System.currentTimeMillis()))

    override fun observableHistorySongs(): LiveData<List<HistoryEntity>> =
        historyDao.observableHistorySongs()

    override fun historySongs(): List<HistoryEntity> = historyDao.historySongs()

    override fun favoritePlaylistLiveData(favorite: String): LiveData<List<SongEntity>> =
        playlistDao.favoritesSongsLiveData(
            playlistDao.playlist(favorite).first().playListId
        )

    override suspend fun favoritePlaylistSongs(favorite: String): List<SongEntity> =
        if (playlistDao.playlist(favorite).isNotEmpty()) playlistDao.favoritesSongs(
            playlistDao.playlist(favorite).first().playListId
        ) else emptyList()

    override suspend fun insertSongInPlayCount(playCountEntity: PlayCountEntity) =
        playCountDao.insertSongInPlayCount(playCountEntity)

    override suspend fun updateSongInPlayCount(playCountEntity: PlayCountEntity) =
        playCountDao.updateSongInPlayCount(playCountEntity)

    override suspend fun deleteSongInPlayCount(playCountEntity: PlayCountEntity) =
        playCountDao.deleteSongInPlayCount(playCountEntity)

    override suspend fun deleteSongInHistory(songId: Long) {
        historyDao.deleteSongInHistory(songId)
    }

    override suspend fun clearSongHistory() {
        historyDao.clearHistory()
    }

    override suspend fun checkSongExistInPlayCount(songId: Long): List<PlayCountEntity> =
        playCountDao.checkSongExistInPlayCount(songId)

    override suspend fun playCountSongs(): List<PlayCountEntity> =
        playCountDao.playCountSongs()

    override fun insertBlacklistPath(blackListStoreEntity: BlackListStoreEntity) =
        blackListStoreDao.insertBlacklistPath(blackListStoreEntity)

    override suspend fun insertBlacklistPath(blackListStoreEntities: List<BlackListStoreEntity>) =
        blackListStoreDao.insertBlacklistPath(blackListStoreEntities)

    override suspend fun insertBlacklistPathAsync(blackListStoreEntity: BlackListStoreEntity) =
        blackListStoreDao.insertBlacklistPath(blackListStoreEntity)

    override suspend fun blackListPaths(): List<BlackListStoreEntity> =
        blackListStoreDao.blackListPaths()

    override suspend fun deleteSongs(songs: List<Song>) = songs.forEach {
        playCountDao.deleteSong(it.id)
    }

    override suspend fun deleteBlacklistPath(blackListStoreEntity: BlackListStoreEntity) =
        blackListStoreDao.deleteBlacklistPath(blackListStoreEntity)

    override suspend fun clearBlacklist() = blackListStoreDao.clearBlacklist()

    override suspend fun isSongFavorite(context: Context, songId: Long): Boolean {
        return playlistDao.isSongExistsInPlaylist(
            playlistDao.playlist(context.getString(R.string.favorites)).firstOrNull()?.playListId
                ?: -1,
            songId
        ).isNotEmpty()
    }
}