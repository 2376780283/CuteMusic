@file:OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class
)

package com.zzh.cutemusic.presentation.screens.playing.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.zzh.cutemusic.R
import com.zzh.cutemusic.data.datastore.rememberArtworkShape
import com.zzh.cutemusic.data.datastore.rememberCarousel
import com.zzh.cutemusic.data.states.MusicState
import com.zzh.cutemusic.domain.actions.PlayerActions
import com.zzh.cutemusic.utils.ImageUtils
import com.zzh.cutemusic.utils.ignoreParentPadding
import com.zzh.cutemusic.utils.toShape
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Artwork(
    pagerModifier: Modifier = Modifier,
    musicState: MusicState,
    onHandlePlayerActions: (PlayerActions) -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val useCarousel by rememberCarousel()
    val artworkShape by rememberArtworkShape()
    val isTablet = configuration.screenWidthDp > 600

    BoxWithConstraints(
        modifier = pagerModifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        val size = if (maxHeight.isSpecified && maxHeight > 0.dp) {
            val fraction = if (isTablet) 0.7f else 0.9f
            minOf(maxWidth * fraction, maxHeight * 0.95f)
        } else {
            val fraction = if (isTablet) 0.6f else 0.9f
            maxWidth * fraction
        }

        val artworkModifier = Modifier
            .size(size)
            .aspectRatio(1f)

        if (useCarousel) {
            val carouselState =
                rememberCarouselState(initialItem = musicState.mediaIndex) { musicState.loadedMedias.size }

            LaunchedEffect(carouselState, musicState.mediaIndex) {
                if (!carouselState.isScrollInProgress &&
                    carouselState.currentItem != musicState.mediaIndex
                ) {
                    carouselState.animateScrollToItem(musicState.mediaIndex)
                }

                // Prevents music switching mid-scroll
                snapshotFlow { carouselState.isScrollInProgress }
                    .collectLatest { isScrolling ->
                        if (!isScrolling) {
                            val settledPage = carouselState.currentItem
                            if (settledPage != musicState.mediaIndex) {
                                if (musicState.shuffle) {
                                    onHandlePlayerActions(PlayerActions.PlayRandom)
                                } else {
                                    onHandlePlayerActions(PlayerActions.SeekToMusicIndex(settledPage))
                                }
                            }
                        }
                    }
            }

            HorizontalCenteredHeroCarousel(
                state = carouselState,
                modifier = artworkModifier.ignoreParentPadding(),
                itemSpacing = 10.dp
            ) { page ->
                val image = rememberAsyncImagePainter(musicState.loadedMedias[page].artUri)
                val imageState by image.state.collectAsStateWithLifecycle()

                when (imageState) {
                    is AsyncImagePainter.State.Error -> {
                        ErrorImage(modifier = Modifier.fillMaxSize())
                    }
                    else -> {
                        Image(
                            painter = image,
                            contentDescription = stringResource(R.string.artwork),
                            modifier = Modifier
                                .fillMaxSize()
                                .maskClip(MaterialTheme.shapes.extraLarge),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

        } else {
            val image =
                rememberAsyncImagePainter(ImageUtils.imageRequester(musicState.track.artUri, context))
            val imageState by image.state.collectAsStateWithLifecycle()

            when (imageState) {
                is AsyncImagePainter.State.Error -> {
                    ErrorImage(modifier = artworkModifier)
                }
                else -> {
                    Image(
                        painter = image,
                        contentDescription = stringResource(R.string.artwork),
                        modifier = artworkModifier
                            .clip(artworkShape.toShape()),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorImage(modifier: Modifier = Modifier) {
    val artworkShape by rememberArtworkShape()

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(artworkShape.toShape())
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.music_note_rounded),
            contentDescription = null,
            modifier = Modifier.size(110.dp),
            tint = contentColorFor(MaterialTheme.colorScheme.surfaceContainer)
        )
    }
}
