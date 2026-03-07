package com.zzh.cutemusic.presentation.screens.lyrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh.cutemusic.data.LyricsParser
import com.zzh.cutemusic.domain.model.Lyrics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LyricsViewModel(
    private val trackPath: String,
    private val lyricsParser: LyricsParser
): ViewModel() {

    private val _lyrics = MutableStateFlow(emptyList<Lyrics>())
    val lyrics = _lyrics.asStateFlow()

    init {
        viewModelScope.launch {
            _lyrics.update {
                lyricsParser.parseLyrics(trackPath)
            }
        }
    }

}