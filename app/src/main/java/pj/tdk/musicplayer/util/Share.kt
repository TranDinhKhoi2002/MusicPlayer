/*
 * Copyright (c) 2020 Hemanth Savarala.
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

package pj.tdk.musicplayer.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import java.io.File

/**
 * Created by hemanths on 2020-02-02.
 */

object Share {
    fun shareStoryToSocial(context: Context, uri: Uri) {
        val feedIntent = Intent(Intent.ACTION_SEND)
        feedIntent.type = "image/*"
        feedIntent.putExtra(Intent.EXTRA_STREAM, uri)
        ActivityCompat.startActivity(context, feedIntent, null)
    }

    fun shareFile(context: Context, file: File) {
        val attachmentUri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName,
            file
        )
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/*"
        sharingIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri)
        context.startActivity(Intent.createChooser(sharingIntent, "send bug report"))
    }
}