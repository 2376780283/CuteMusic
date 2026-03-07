package com.zzh.cutemusic.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh.cutemusic.data.models.Folder
import com.zzh.cutemusic.domain.repository.FoldersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoldersViewModel(
    private val foldersRepository: FoldersRepository
) : ViewModel() {

    private val _folders = MutableStateFlow(emptyList<Folder>())
    val folders = _folders.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _folders.update { foldersRepository.fetchFoldersWithMusics() }
        }
    }

}