/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package pj.tdk.musicplayer.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore.Audio.AudioColumns
import android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC
import android.provider.MediaStore.Audio.Playlists.Members
import androidx.core.database.getStringOrNull
import pj.tdk.musicplayer.Constants.IS_MUSICs
import pj.tdk.musicplayer.extensions.getStringOrNull
import pj.tdk.musicplayer.models.AbsCustomPlaylist
import pj.tdk.musicplayer.models.Playlist
import pj.tdk.musicplayer.models.PlaylistSong
import pj.tdk.musicplayer.models.Song

/**
 * Created by hemanths on 16/08/17.
 */

object PlaylistSongsLoader {

    fun getPlaylistSongList(
        context: Context,
        playlist: Playlist
    ): List<Song> {
        return if (playlist is AbsCustomPlaylist) {
            return playlist.songs()
        } else {
            getPlaylistSongList(context, playlist.id)
        }
    }

    @JvmStatic
    fun getPlaylistSongList(context: Context, playlistId: Long): List<Song> {
        val songs = mutableListOf<Song>()
        val cursor =
            makePlaylistSongCursor(
                context,
                playlistId
            )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(
                    getPlaylistSongFromCursorImpl(
                        cursor,
                        playlistId
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return songs
    }

    // TODO duplicated in [PlaylistRepository.kt]
    private fun getPlaylistSongFromCursorImpl(cursor: Cursor, playlistId: Long): PlaylistSong {
        val id = cursor.getLong(Members.AUDIO_ID.toInt())
        val title = cursor.getString(AudioColumns.TITLE.toInt())
        val trackNumber = cursor.getInt(AudioColumns.TRACK.toInt())
        val year = cursor.getInt(AudioColumns.YEAR.toInt())
        val duration = cursor.getLong(AudioColumns.DURATION.toInt())
        val data = cursor.getString(AudioColumns.DATA.toInt())
        val dateModified = cursor.getLong(AudioColumns.DATE_MODIFIED.toInt())
        val albumId = cursor.getLong(AudioColumns.ALBUM_ID.toInt())
        val albumName = cursor.getString(AudioColumns.ALBUM.toInt())
        val artistId = cursor.getLong(AudioColumns.ARTIST_ID.toInt())
        val artistName = cursor.getString(AudioColumns.ARTIST.toInt())
        val idInPlaylist = cursor.getLong(Members._ID.toInt())
        val composer = cursor.getStringOrNull(AudioColumns.COMPOSER.toInt())
        val albumArtist = cursor.getStringOrNull("album_artist".toInt())
        return PlaylistSong(
            id,
            title,
            trackNumber,
            year,
            duration,
            data,
            dateModified,
            albumId,
            albumName,
            artistId,
            artistName,
            playlistId,
            idInPlaylist,
            composer,
            albumArtist
        )
    }

    private fun makePlaylistSongCursor(context: Context, playlistId: Long): Cursor? {
        try {
            return context.contentResolver.query(
                Members.getContentUri("external", playlistId),
                arrayOf(
                    Members.AUDIO_ID, // 0
                    AudioColumns.TITLE, // 1
                    AudioColumns.TRACK, // 2
                    AudioColumns.YEAR, // 3
                    AudioColumns.DURATION, // 4
                    AudioColumns.DATA, // 5
                    AudioColumns.DATE_MODIFIED, // 6
                    AudioColumns.ALBUM_ID, // 7
                    AudioColumns.ALBUM, // 8
                    AudioColumns.ARTIST_ID, // 9
                    AudioColumns.ARTIST, // 10
                    Members._ID,//11
                    AudioColumns.COMPOSER,//12
                    "album_artist"//13
                ), IS_MUSICs, null, Members.DEFAULT_SORT_ORDER
            )
        } catch (e: SecurityException) {
            return null
        }
    }
}
