package com.zzh.cutemusic.domain.repository

import com.zzh.cutemusic.data.models.PlaybackStat
import com.zzh.cutemusic.data.playlist.PlaylistDao
import kotlinx.coroutines.flow.Flow

class PlaybackStatsRepository(
    private val dao: PlaylistDao
) {
    suspend fun upsertPlaybackStat(playbackStat: PlaybackStat) = dao.upsertPlaybackStat(playbackStat)

    suspend fun getPlaybackStat(mediaId: String) = dao.getPlaybackStat(mediaId)

    fun getTopPlayedTracks(): Flow<List<PlaybackStat>> = dao.getTopPlayedTracks()
}
