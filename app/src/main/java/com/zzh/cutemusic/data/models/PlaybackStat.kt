package com.zzh.cutemusic.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaybackStat(
    @PrimaryKey val mediaId: String,
    val totalPlayTimeMs: Long = 0,
    val playCount: Int = 0,
    val lastPlayedTimestamp: Long = 0
)
