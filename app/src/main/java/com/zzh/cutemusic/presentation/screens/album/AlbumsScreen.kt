@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)

package com.zzh.cutemusic.presentation.screens.album

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zzh.cutemusic.R
import com.zzh.cutemusic.data.datastore.rememberAlbumsUseGrid
import com.zzh.cutemusic.data.datastore.rememberAlbumGrids
import com.zzh.cutemusic.data.datastore.rememberAlbumSort
import com.zzh.cutemusic.data.datastore.rememberListItemSize
import com.zzh.cutemusic.data.states.MusicState
import com.zzh.cutemusic.domain.actions.PlayerActions
import com.zzh.cutemusic.presentation.navigation.Screen
import com.zzh.cutemusic.presentation.screens.album.components.AlbumCard
import com.zzh.cutemusic.presentation.shared_components.CuteSearchbar
import com.zzh.cutemusic.presentation.shared_components.NoResult
import com.zzh.cutemusic.presentation.shared_components.NoXFound
import com.zzh.cutemusic.presentation.shared_components.SortingDropdownMenu
import com.zzh.cutemusic.utils.AlbumSort
import com.zzh.cutemusic.utils.ImageUtils
import com.zzh.cutemusic.utils.ordered
import com.zzh.cutemusic.utils.selfAlignHorizontally

@Composable
fun SharedTransitionScope.AlbumsScreen(
    state: AlbumsState,
    musicState: MusicState,
    onNavigate: (Screen) -> Unit,
    onHandlePlayerActions: (PlayerActions) -> Unit,
) {
    val context = LocalContext.current
    val textFieldState = rememberTextFieldState()
    var isSortedByASC by rememberSaveable { mutableStateOf(true) } // I prolly should change this
    var albumSort by rememberAlbumSort()
    val lazyState = rememberLazyGridState()
    val listState = rememberLazyListState()
    var numberOfAlbumGrids by rememberAlbumGrids()
    var useGrid by rememberAlbumsUseGrid()
    var listItemSize by rememberListItemSize()

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                CuteSearchbar(
                    modifier = Modifier.selfAlignHorizontally(),
                    textFieldState = textFieldState,
                    showSearchField = true,
                    musicState = musicState,
                    sortingMenu = {
                        SortingDropdownMenu(
                            isSortedAscending = isSortedByASC,
                            onChangeSorting = { isSortedByASC = it },
                            topContent = {
                                if (useGrid) {
                                    DropdownMenuItem(
                                        onClick = {
                                            numberOfAlbumGrids =
                                                if (numberOfAlbumGrids == 4) 2 else numberOfAlbumGrids + 1
                                        },
                                        text = { Text(stringResource(R.string.no_of_grids)) },
                                        trailingIcon = { Text("$numberOfAlbumGrids") }
                                    )
                                } else {
                                    DropdownMenuItem(
                                        onClick = {
                                            listItemSize = if (listItemSize >= 80) 40 else listItemSize + 10
                                        },
                                        text = { Text(stringResource(R.string.cover_size)) },
                                        trailingIcon = { Text("$listItemSize") }
                                    )
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        useGrid = !useGrid
                                    },
                                    text = {
                                        Text(
                                            if (useGrid) stringResource(R.string.switch_to_list)
                                            else stringResource(R.string.switch_to_grid)
                                        )
                                    },
                                    trailingIcon = {
                                        Icon(
                                            painter = painterResource(
                                                if (useGrid) R.drawable.grid_view
                                                else R.drawable.list
                                            ),
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        ) {
                            repeat(2) { i ->
                                val text = when (i) {
                                    0 -> R.string.title
                                    1 -> R.string.artist
                                    else -> throw IndexOutOfBoundsException()
                                }
                                DropdownMenuItem(
                                    selected = albumSort == i,
                                    onClick = { albumSort = i },
                                    shapes = MenuDefaults.itemShapes(),
                                    colors = MenuDefaults.selectableItemColors(),
                                    text = { Text(stringResource(text)) },
                                    trailingIcon = {
                                        if (albumSort == i) {
                                            Icon(
                                                painter = painterResource(R.drawable.check),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    },
                    onHandlePlayerActions = onHandlePlayerActions,
                    onNavigate = onNavigate
                )
            }
        ) { paddingValues ->
            val orderedAlbums = state.albums.ordered(
                sort = AlbumSort.entries.getOrElse(albumSort) { AlbumSort.NAME },
                ascending = isSortedByASC,
                query = textFieldState.text.toString()
            )

            if (useGrid) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(if (orderedAlbums.isEmpty() || state.albums.isEmpty()) 1 else maxOf(1, numberOfAlbumGrids)),
                    contentPadding = paddingValues,
                    state = lazyState
                ) {
                    if (state.albums.isEmpty()) {
                        item {
                            NoXFound(
                                headlineText = R.string.no_albums_found,
                                bodyText = R.string.no_album_desc,
                                icon = R.drawable.album_filled
                            )
                        }
                    } else {
                        if (orderedAlbums.isEmpty()) {
                            item { NoResult() }
                        } else {
                            items(
                                items = orderedAlbums,
                                key = { it.id.toString() + it.name }
                            ) { album ->
                                AlbumCard(
                                    modifier = Modifier.animateItem(),
                                    album = album,
                                    onClick = { onNavigate(Screen.AlbumsDetails(album.name)) }
                                )
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    if (state.albums.isEmpty()) {
                        item {
                            NoXFound(
                                headlineText = R.string.no_albums_found,
                                bodyText = R.string.no_album_desc,
                                icon = R.drawable.album_filled
                            )
                        }
                    } else {
                        if (orderedAlbums.isEmpty()) {
                            item { NoResult() }
                        } else {
                            items(
                                items = orderedAlbums,
                                key = { it.id.toString() + it.name }
                            ) { album ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { onNavigate(Screen.AlbumsDetails(album.name)) }
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = ImageUtils.imageRequester(
                                            album.id,
                                            context
                                        ),
                                        contentDescription = stringResource(R.string.artwork),
                                        modifier = Modifier
                                            .size(listItemSize.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = album.name,
                                            style = MaterialTheme.typography.titleMediumEmphasized,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = album.artist,
                                            style = MaterialTheme.typography.bodyMediumEmphasized.copy(
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

