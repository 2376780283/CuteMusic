package com.zzh.cutemusic.presentation.shared_components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zzh.cutemusic.R
import com.zzh.cutemusic.domain.actions.PlayerActions
import com.zzh.cutemusic.presentation.shared_components.animations.AnimatedSlider

@Composable
fun VolumeDialog(
    volume: Int,
    onDismissRequest: () -> Unit,
    onHandlePlayerAction: (PlayerActions) -> Unit
) {
    val animatedVolume by animateIntAsState(
        targetValue = volume,
        animationSpec = tween(300),
        label = "volume"
    )

    val volumeTint by animateColorAsState(
        targetValue = if (volume != 100) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(500),
        label = "volumeTint"
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.okay))
            }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.volume),
                contentDescription = null
            )
        },
        title = { Text(stringResource(id = R.string.player_volume)) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$animatedVolume%",
                        style = MaterialTheme.typography.headlineMedium,
                        color = volumeTint
                    )
                    IconButton(
                        onClick = {
                            onHandlePlayerAction(PlayerActions.SetVolume(100))
                        },
                        enabled = volume != 100
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.reset),
                            contentDescription = null,
                            tint = volumeTint
                        )
                    }
                }
                
                AnimatedSlider(
                    value = volume.toFloat(),
                    onValueChange = { newVolume ->
                        onHandlePlayerAction(PlayerActions.SetVolume(newVolume.toInt()))
                    },
                    valueRange = 0f..100f
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "0%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "100%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    )
}
