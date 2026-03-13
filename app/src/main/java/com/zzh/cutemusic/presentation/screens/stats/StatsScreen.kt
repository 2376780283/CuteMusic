@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package com.zzh.cutemusic.presentation.screens.stats

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zzh.cutemusic.R
import com.zzh.cutemusic.data.states.MusicState
import com.zzh.cutemusic.domain.actions.PlayerActions
import com.zzh.cutemusic.presentation.navigation.Screen
import com.zzh.cutemusic.presentation.shared_components.MusicListItem
import com.zzh.cutemusic.utils.formatToReadableTime

@Composable
fun SharedTransitionScope.StatsScreen(
    viewModel: StatsViewModel,
    musicState: MusicState,
    onNavigate: (Screen) -> Unit,
    onHandlePlayerAction: (PlayerActions) -> Unit
) {
    val topPlayedTracks by viewModel.topPlayedTracks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.top_played)) }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(topPlayedTracks) { stat ->
                Column {
                    MusicListItem(
                        music = stat.track,
                        musicState = musicState,
                        onShortClick = {
                            onHandlePlayerAction(
                                PlayerActions.Play(
                                    tracks = listOf(stat.track),
                                    index = 0
                                )
                            )
                        },
                        onNavigate = onNavigate,
                        onHandlePlayerActions = onHandlePlayerAction
                    )
                    Text(
                        text = stringResource(
                            R.string.play_stats_format,
                            stat.playCount,
                            stat.totalPlayTimeMs.formatToReadableTime()
                        ),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 72.dp, bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
