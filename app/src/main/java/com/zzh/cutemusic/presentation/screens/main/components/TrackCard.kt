@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalFoundationApi::class)

package com.zzh.cutemusic.presentation.screens.main.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.zzh.cutemusic.R
import com.zzh.cutemusic.data.models.CuteTrack
import com.zzh.cutemusic.data.states.MusicState
import com.zzh.cutemusic.domain.actions.PlayerActions
import com.zzh.cutemusic.presentation.navigation.Screen
import com.zzh.cutemusic.presentation.shared_components.SelectedItemLogo
import com.zzh.cutemusic.presentation.shared_components.TrackDropdownMenu
import com.zzh.cutemusic.utils.ImageUtils

@Composable
fun SharedTransitionScope.TrackCard(
    modifier: Modifier = Modifier,
    music: CuteTrack,
    musicState: MusicState,
    onShortClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onNavigate: (Screen) -> Unit,
    onHandlePlayerActions: (PlayerActions) -> Unit,
    isSelected: Boolean = false
) {
    val context = LocalContext.current
    var isDropDownExpanded by remember { mutableStateOf(false) }
    
    val image = rememberAsyncImagePainter(ImageUtils.imageRequester(music.artUri, context))
    val imageState by image.state.collectAsStateWithLifecycle()
    
    val bgColor by animateColorAsState(
        targetValue = if (musicState.track.uri.toString() == music.uri.toString() && musicState.isPlayerReady) {
            MaterialTheme.colorScheme.primaryContainer.copy(0.1f)
        } else {
            Color.Transparent
        }
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.9f else 1f
    )

    Column(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .combinedClickable(
                onClick = onShortClick,
                onLongClick = onLongClick
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(15.dp))
        ) {
            AnimatedContent(
                targetState = isSelected,
                transitionSpec = { scaleIn() togetherWith scaleOut() },
                modifier = Modifier.matchParentSize()
            ) { selected ->
                if (selected) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        SelectedItemLogo()
                    }
                } else {
                    when (imageState) {
                        is AsyncImagePainter.State.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surfaceContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.music_note_rounded),
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = contentColorFor(MaterialTheme.colorScheme.surfaceContainer)
                                )
                            }
                        }
                        else -> {
                            AsyncImage(
                                model = ImageUtils.imageRequester(music.artUri, context),
                                contentDescription = stringResource(id = R.string.artwork),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(Modifier.height(8.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = music.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmallEmphasized,
                    modifier = Modifier.basicMarquee()
                )
                Text(
                    text = music.artist,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmallEmphasized.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier.basicMarquee()
                )
            }
            
            IconButton(
                onClick = { isDropDownExpanded = true },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_vert),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        TrackDropdownMenu(
            track = music,
            musicState = musicState,
            isExpanded = isDropDownExpanded,
            onDismissRequest = { isDropDownExpanded = false },
            onNavigate = onNavigate,
            onHandlePlayerActions = onHandlePlayerActions
        )
    }
}
