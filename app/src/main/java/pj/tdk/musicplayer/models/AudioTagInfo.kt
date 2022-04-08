package pj.tdk.musicplayer.models

import org.jaudiotagger.tag.FieldKey
import pj.tdk.musicplayer.models.ArtworkInfo

class AudioTagInfo(
    val filePaths: List<String>?,
    val fieldKeyValueMap: Map<FieldKey, String>?,
    val artworkInfo: ArtworkInfo?
)