package com.zzh.cutemusic.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh.cutemusic.data.models.CuteTrack
import com.zzh.cutemusic.data.models.PlaybackStat
import com.zzh.cutemusic.domain.repository.PlaybackStatsRepository
import com.zzh.cutemusic.data.AbstractTracksScanner
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class TrackStats(
    val track: CuteTrack,
    val totalPlayTimeMs: Long,
    val playCount: Int,
    val lastPlayedTimestamp: Long
)

class StatsViewModel(
    private val playbackStatsRepository: PlaybackStatsRepository,
    private val tracksScanner: AbstractTracksScanner
) : ViewModel() {

    val topPlayedTracks: StateFlow<List<TrackStats>> = combine(
        playbackStatsRepository.getTopPlayedTracks(),
        tracksScanner.fetchLatestTracks(null, null)
    ) { stats: List<PlaybackStat>, allTracks: List<CuteTrack> ->
        stats.mapNotNull { stat: PlaybackStat ->
            val track: CuteTrack? = allTracks.find { it.mediaId == stat.mediaId }
            if (track != null) {
                TrackStats(
                    track = track,
                    totalPlayTimeMs = stat.totalPlayTimeMs,
                    playCount = stat.playCount,
                    lastPlayedTimestamp = stat.lastPlayedTimestamp
                )
            } else {
                null
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
