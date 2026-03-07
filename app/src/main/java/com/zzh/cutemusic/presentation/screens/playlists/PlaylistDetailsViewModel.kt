@file:OptIn(ExperimentalCoroutinesApi::class)

package com.zzh.cutemusic.presentation.screens.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh.cutemusic.data.datastore.UserPreferences
import com.zzh.cutemusic.data.models.CuteTrack
import com.zzh.cutemusic.data.models.Playlist
import com.zzh.cutemusic.data.playlist.PlaylistDao
import com.zzh.cutemusic.domain.actions.PlaylistActions
import com.zzh.cutemusic.domain.repository.PlaylistsRepository
import com.zzh.cutemusic.utils.ordered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(
    private val id: Int,
    private val userPreferences: UserPreferences,
    private val playlistsRepository: PlaylistsRepository,
    private val dao: PlaylistDao
) : ViewModel() {


    private val _state = MutableStateFlow(PlaylistDetailsState(isLoading = true))
    val state = _state.asStateFlow()


    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            dao.getPlaylistDetails(id)
//                .flatMapLatest { playlist ->
//                    _state.update { it.copy(playlist = playlist) }
//                    playlistsRepository.fetchLatestPlaylistTracks(playlist.musics)
//                }.collectLatest { tracks ->
//                    _state.update {
//                        it.copy(
//                            tracks = tracks,
//                            isLoading = false
//                        )
//                    }
//                }
//        }

        viewModelScope.launch {
            dao.getPlaylistDetails(id)
                .flatMapLatest { playlist ->
                    combine(
                        playlistsRepository.fetchLatestPlaylistTracks(playlist.musics),
                        userPreferences.getTrackSort,
                        userPreferences.getSortTracksAscending
                    ) { tracks, sort, ascending ->
                        println("loof - ${tracks.ordered(sort, ascending, query = "")}")
                        playlist to tracks.ordered(sort, ascending, query = "")
                    }
                }
                .flowOn(Dispatchers.Default)
                .collectLatest { (playlist, sortedTracks) ->
                    _state.update {
                        it.copy(
                            playlist = playlist,
                            tracks = sortedTracks,
                            isLoading = false
                        )
                    }
                }
        }


    }

    fun handlePlaylistActions(action: PlaylistActions) {
        when (action) {
            is PlaylistActions.UpsertPlaylist -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.upsertPlaylist(action.playlist)
                }
            }

            else -> Unit
        }
    }

}

data class PlaylistDetailsState(
    val isLoading: Boolean = false,
    val playlist: Playlist = Playlist(),
    val tracks: List<CuteTrack> = emptyList()
)