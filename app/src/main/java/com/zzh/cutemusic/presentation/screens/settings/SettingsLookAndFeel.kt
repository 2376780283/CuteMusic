@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.zzh.cutemusic.presentation.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zzh.cutemusic.R
import com.zzh.cutemusic.data.datastore.rememberAlbumGrids
import com.zzh.cutemusic.data.datastore.rememberAppTheme
import com.zzh.cutemusic.data.datastore.rememberShowShuffleButton
import com.zzh.cutemusic.data.datastore.rememberTrackGrids
import com.zzh.cutemusic.data.datastore.rememberUseArtTheme
import com.zzh.cutemusic.data.datastore.rememberUseExpressivePalette
import com.zzh.cutemusic.data.datastore.rememberUseSystemFont
import com.zzh.cutemusic.presentation.screens.settings.compenents.FontSelector
import com.zzh.cutemusic.presentation.screens.settings.compenents.SettingsCards
import com.zzh.cutemusic.presentation.screens.settings.compenents.SettingsWithTitle
import com.zzh.cutemusic.presentation.screens.settings.compenents.SliderSettingsCards
import com.zzh.cutemusic.presentation.screens.settings.compenents.ThemeSelector
import com.zzh.cutemusic.presentation.shared_components.CuteNavigationButton
import com.zzh.cutemusic.presentation.shared_components.LazyRowWithScrollButton
import com.zzh.cutemusic.presentation.theme.nunitoFontFamily
import com.zzh.cutemusic.utils.CuteTheme
import com.zzh.cutemusic.utils.anyDarkColorScheme
import com.zzh.cutemusic.utils.anyLightColorScheme

@Composable
fun SettingsLookAndFeel(
    onNavigateUp: () -> Unit
) {
    val scrollState = rememberScrollState()
    var theme by rememberAppTheme()
    var useSystemFont by rememberUseSystemFont()
    var showShuffleButton by rememberShowShuffleButton()
    var useMaterialArt by rememberUseArtTheme()
    var useExpressivePalette by rememberUseExpressivePalette()
    var numberOfAlbumGrids by rememberAlbumGrids()
    var numberOfTrackGrids by rememberTrackGrids()

    val darkColorScheme = anyDarkColorScheme()
    val lightColorScheme = anyLightColorScheme()
    val systemInDark = isSystemInDarkTheme()

    val themeItems = remember(theme, systemInDark, darkColorScheme, lightColorScheme) {
        listOf(
            ThemeItem(
                onClick = { theme = CuteTheme.SYSTEM },
                backgroundColor = if (systemInDark) darkColorScheme.background else lightColorScheme.background,
                text = "System", 
                isSelected = theme == CuteTheme.SYSTEM,
                iconAndTint = Pair(
                    R.drawable.system_theme,
                    if (systemInDark) darkColorScheme.onBackground else lightColorScheme.onBackground
                )
            ),
            ThemeItem(
                onClick = { theme = CuteTheme.DARK },
                backgroundColor = darkColorScheme.background,
                text = "Dark",
                isSelected = theme == CuteTheme.DARK,
                iconAndTint = Pair(
                    R.drawable.dark_mode,
                    darkColorScheme.onBackground
                )
            ),
            ThemeItem(
                onClick = { theme = CuteTheme.LIGHT },
                backgroundColor = lightColorScheme.background,
                text = "Light",
                isSelected = theme == CuteTheme.LIGHT,
                iconAndTint = Pair(
                    R.drawable.light_mode,
                    lightColorScheme.onBackground
                )
            ),
            ThemeItem(
                onClick = { theme = CuteTheme.AMOLED },
                backgroundColor = Color.Black,
                text = "Amoled",
                isSelected = theme == CuteTheme.AMOLED,
                iconAndTint = Pair(R.drawable.amoled, Color.White)
            )
        )
    }

    // Since stringResource is a composable, we should get labels outside remember or use them inside ThemeSelector
    val systemLabel = stringResource(R.string.system)
    val darkLabel = stringResource(R.string.dark_mode)
    val lightLabel = stringResource(R.string.light_mode)
    val amoledLabel = stringResource(R.string.amoled_mode)
    
    val displayThemeItems = remember(themeItems, systemLabel, darkLabel, lightLabel, amoledLabel) {
        themeItems.mapIndexed { index, item ->
            item.copy(text = when(index) {
                0 -> systemLabel
                1 -> darkLabel
                2 -> lightLabel
                else -> amoledLabel
            })
        }
    }

    val fontItems = remember(useSystemFont) {
        listOf(
            FontItem(
                onClick = { useSystemFont = false },
                fontStyle = FontStyle.DEFAULT,
                borderColor = if (!useSystemFont) Color.Unspecified else Color.Transparent, // Use placeholder to resolve later
                text = {
                    Text(
                        text = "Tt",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
            ),
            FontItem(
                onClick = { useSystemFont = true },
                fontStyle = FontStyle.SYSTEM,
                borderColor = if (useSystemFont) Color.Unspecified else Color.Transparent,
                text = {
                    Text(
                        text = "Tt",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        )
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val correctedFontItems = remember(fontItems, primaryColor) {
        fontItems.map { it.copy(borderColor = if (it.borderColor == Color.Unspecified) primaryColor else it.borderColor) }
    }

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
                title = R.string.theme
            ) {
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    LazyRowWithScrollButton(
                        items = displayThemeItems
                    ) { theme ->
                        ThemeSelector(theme)
                    }
                }
            }
            SettingsWithTitle(
                title = R.string.font
            ) {
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    LazyRowWithScrollButton(
                        items = correctedFontItems
                    ) { font ->
                        FontSelector(font)
                    }
                }
            }
            SettingsWithTitle(
                title = R.string.ui
            ) {
                SettingsCards(
                    checked = useMaterialArt,
                    onCheckedChange = { useMaterialArt = !useMaterialArt },
                    topDp = 24.dp,
                    bottomDp = 4.dp,
                    text = stringResource(R.string.use_art)
                )
                SettingsCards(
                    checked = useExpressivePalette,
                    onCheckedChange = { useExpressivePalette = !useExpressivePalette },
                    topDp = 4.dp,
                    bottomDp = 24.dp,
                    text = stringResource(R.string.use_expr_palette)
                )
            }
            SettingsWithTitle(
                title = R.string.grid
            ) {
                SliderSettingsCards(
                    value = numberOfAlbumGrids,
                    onValueChange = { numberOfAlbumGrids = it },
                    topDp = 24.dp,
                    bottomDp = 4.dp,
                    text = R.string.no_of_album_grids,
                    valueRange = 2f..4f
                )
                SliderSettingsCards(
                    value = numberOfTrackGrids,
                    onValueChange = { numberOfTrackGrids = it },
                    topDp = 4.dp,
                    bottomDp = 24.dp,
                    text = R.string.no_of_track_grids,
                    valueRange = 2f..4f
                )
            }
            SettingsWithTitle(
                title = R.string.cute_searchbar
            ) {
                SettingsCards(
                    checked = showShuffleButton,
                    onCheckedChange = { showShuffleButton = !showShuffleButton },
                    topDp = 24.dp,
                    bottomDp = 24.dp,
                    text = stringResource(R.string.show_shuffle_btn)
                )
            }
        }
    }
}

@Immutable
data class ThemeItem(
    val onClick: () -> Unit,
    val backgroundColor: Color,
    val text: String,
    val isSelected: Boolean,
    val iconAndTint: Pair<Int, Color>
)

@Immutable
data class FontItem(
    val onClick: () -> Unit,
    val fontStyle: FontStyle,
    val borderColor: Color,
    val text: @Composable () -> Unit
)

enum class FontStyle {
    DEFAULT,
    SYSTEM
}