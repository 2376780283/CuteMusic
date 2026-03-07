@file:OptIn(FlowPreview::class)

package com.zzh.cutemusic.presentation.screens.main

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh.cutemusic.data.AbstractTracksScanner
import com.zzh.cutemusic.data.datastore.UserPreferences
import com.zzh.cutemusic.data.models.CuteTrack
import com.zzh.cutemusic.utils.ordered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val abstractTracksScanner: AbstractTracksScanner,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val textFieldState = TextFieldState()
    private val userQuery = snapshotFlow { textFieldState.text }.debounce(250)

    private val _state = MutableStateFlow(MainState(isLoading = true))
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            combine(
                abstractTracksScanner.fetchLatestTracks(null, null),
                userQuery,
                userPreferences.getTrackSort,
                userPreferences.getSortTracksAscending
            ) { tracks, query, trackSort, ascending ->
                tracks.ordered(trackSort, ascending, query.toString())
            }
                .flowOn(Dispatchers.Default)
                .collectLatest { newTracks ->
                    _state.update {
                        it.copy(
                            tracks = newTracks,
                            isLoading = false
                        )
                    }
                }
        }
    }


}


data class MainState(
    val isLoading: Boolean = false,
    val tracks: List<CuteTrack> = emptyList()
)