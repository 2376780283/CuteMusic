@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.zzh.cutemusic.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zzh.cutemusic.R
import com.zzh.cutemusic.data.datastore.rememberPauseOnMute
import com.zzh.cutemusic.data.datastore.rememberPlayerVolume
import com.zzh.cutemusic.data.states.MusicState
import com.zzh.cutemusic.domain.actions.PlayerActions
import com.zzh.cutemusic.presentation.screens.settings.compenents.SettingsCards
import com.zzh.cutemusic.presentation.screens.settings.compenents.SettingsWithTitle
import com.zzh.cutemusic.presentation.screens.settings.compenents.SliderSettingsCards
import com.zzh.cutemusic.presentation.shared_components.CuteNavigationButton

@Composable
fun SettingsPlayback(
    musicState: MusicState,
    onHandlePlayerActions: (PlayerActions) -> Unit,
    onNavigateUp: () -> Unit
) {

    val scrollState = rememberScrollState()
    var pauseOnMute by rememberPauseOnMute()
    var playerVolume by rememberPlayerVolume()


    Scaffold(
        bottomBar = {
            CuteNavigationButton(onNavigateUp = onNavigateUp)
        }
    ) { pv ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(pv)
        ) {
            SettingsWithTitle(
                title = R.string.audio
            ) {
                SliderSettingsCards(
                    value = playerVolume,
                    topDp = 24.dp,
                    bottomDp = 8.dp,
                    text = R.string.player_volume,
                    onValueChange = { volume ->
                        playerVolume = volume
                        onHandlePlayerActions(PlayerActions.SetVolume(volume))
                    },
                    optionalDescription = R.string.player_volume_desc,
                    valueRange = 0f..100f
                )
                SettingsCards(
                    checked = pauseOnMute,
                    onCheckedChange = { pauseOnMute = !pauseOnMute },
                    topDp = 8.dp,
                    bottomDp = 24.dp,
                    text = stringResource(R.string.pause_on_mute),
                    optionalDescription = R.string.pause_on_mute_desc
                )
            }
        }
    }

}