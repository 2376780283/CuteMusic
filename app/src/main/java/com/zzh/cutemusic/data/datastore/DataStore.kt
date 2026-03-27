package com.zzh.cutemusic.data.datastore

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zzh.cutemusic.utils.ArtworkShape
import com.zzh.cutemusic.utils.CuteTheme
import com.zzh.cutemusic.utils.SliderStyle

private const val PREFERENCES_NAME = "settings"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

object PreferencesKeys {
    val THEME = stringPreferencesKey("theme")
    val USE_SYSTEM_FONT = booleanPreferencesKey("use_sys_font")
    val WHITELISTED_FOLDERS = stringSetPreferencesKey("WHITELISTED_FOLDERS")
    val HAS_SEEN_TIP = booleanPreferencesKey("has_seen_tip")
    val SNAP_SPEED_N_PITCH = booleanPreferencesKey("snap_peed_n_pitch")
    val KILL_SERVICE = booleanPreferencesKey("kill_service")
    val USE_ART_THEME = booleanPreferencesKey("use_art_theme")
    val SHOW_X_BUTTON = booleanPreferencesKey("show_x_button")
    val SHOW_SHUFFLE_BUTTON = booleanPreferencesKey("show_shuffle_button")
    val SAF_TRACKS = stringSetPreferencesKey("saf_tracks")
    val GROUP_BY_FOLDERS = booleanPreferencesKey("GROUP_BY_FOLDERS")
    val CAROUSEL = booleanPreferencesKey("CAROUSEL")
    val MEDIA_INDEX_TO_MEDIA_ID = stringPreferencesKey("MEDIA_INDEX_TO_MEDIA_ID")
    val NUMBER_OF_ALBUM_GRIDS = intPreferencesKey("NUMBER_OF_ALBUM_GRIDS")
    val NUMBER_OF_TRACK_GRIDS = intPreferencesKey("NUMBER_OF_TRACK_GRIDS")
    val SLIDER_STYLE = stringPreferencesKey("SLIDER_STYLE")
    val THUMBLESS_SLIDER = booleanPreferencesKey("THUMBLESS_SLIDER")
    val HIDDEN_FOLDERS = stringSetPreferencesKey("HIDDEN_FOLDERS")
    val ART_AS_BACKGROUND = booleanPreferencesKey("ART_AS_BACKGROUND")
    val ALBUM_SORT = intPreferencesKey("ALBUM_SORT")
    val TRACK_SORT = intPreferencesKey("TRACK_SORT")
    val ARTIST_SORT = intPreferencesKey("ARTIST_SORT")
    val PAUSE_ON_MUTE = booleanPreferencesKey("PAUSE_ON_MUTE")
    val USE_EXPRESSIVE_PALETTE = booleanPreferencesKey("USE_EXPRESSIVE_PALETTE")
    val MIN_TRACK_DURATION = intPreferencesKey("MIN_TRACK_DURATION")
    val PLAYLIST_SORT = intPreferencesKey("PLAYLIST_SORT")
    val ARTWORK_SHAPE = stringPreferencesKey("ARTWORK_SHAPE")
    val HAS_BEEN_THROUGH_SETUP = booleanPreferencesKey("HAS_BEEN_THROUGH_SETUP")
    val SORT_TRACKS_ASCENDING = booleanPreferencesKey("SORT_TRACKS_ASCENDING")
    val LAST_MUSIC_STATE = stringPreferencesKey("LAST_MUSIC_STATE")
    val PLAYER_VOLUME = intPreferencesKey("PLAYER_VOLUME")
    val MAIN_SCREEN_USE_GRID = booleanPreferencesKey("MAIN_SCREEN_USE_GRID")
    val ALBUMS_USE_GRID = booleanPreferencesKey("ALBUMS_USE_GRID")
    val PLAYLISTS_USE_GRID = booleanPreferencesKey("PLAYLISTS_USE_GRID")
    val ARTIST_USE_GRID = booleanPreferencesKey("ARTIST_USE_GRID")
    val LIST_ITEM_SIZE = intPreferencesKey("LIST_ITEM_SIZE")
}


@Composable
fun rememberAppTheme() =
    rememberPreference(key = PreferencesKeys.THEME, defaultValue = CuteTheme.SYSTEM)

@Composable
fun rememberUseSystemFont() =
    rememberPreference(key = PreferencesKeys.USE_SYSTEM_FONT, defaultValue = false)

@Composable
fun rememberSnapSpeedAndPitch() =
    rememberPreference(key = PreferencesKeys.SNAP_SPEED_N_PITCH, defaultValue = false)

@Composable
fun rememberUseArtTheme() =
    rememberPreference(key = PreferencesKeys.USE_ART_THEME, defaultValue = false)

@Composable
fun rememberShowShuffleButton() =
    rememberPreference(key = PreferencesKeys.SHOW_SHUFFLE_BUTTON, defaultValue = true)

@Composable
fun rememberAllSafTracks() =
    rememberPreference(key = PreferencesKeys.SAF_TRACKS, defaultValue = emptySet())

@Composable
fun rememberGroupByFolders() =
    rememberPreference(key = PreferencesKeys.GROUP_BY_FOLDERS, defaultValue = false)

@Composable
fun rememberCarousel() =
    rememberPreference(key = PreferencesKeys.CAROUSEL, defaultValue = false)

@Composable
fun rememberAlbumGrids() =
    rememberPreference(key = PreferencesKeys.NUMBER_OF_ALBUM_GRIDS, defaultValue = 2)
    
@Composable
fun rememberTrackGrids() =
    rememberPreference(key = PreferencesKeys.NUMBER_OF_TRACK_GRIDS, defaultValue = 2)

@Composable
fun rememberArtworkShape() =
    rememberPreference(key = PreferencesKeys.ARTWORK_SHAPE, defaultValue = ArtworkShape.CLASSIC)

@Composable
fun rememberSliderStyle() =
    rememberPreference(key = PreferencesKeys.SLIDER_STYLE, defaultValue = SliderStyle.WAVY)

@Composable
fun rememberThumblessSlider() =
    rememberPreference(key = PreferencesKeys.THUMBLESS_SLIDER, defaultValue = false)

@Composable
fun rememberHiddenFolders() =
    rememberPreference(key = PreferencesKeys.HIDDEN_FOLDERS, defaultValue = emptySet())

@Composable
fun rememberUseArtAsBackground() =
    rememberPreference(key = PreferencesKeys.ART_AS_BACKGROUND, defaultValue = false)

@Composable
fun rememberAlbumSort() =
    rememberPreference(key = PreferencesKeys.ALBUM_SORT, defaultValue = 0)

@Composable
fun rememberTrackSort() =
    rememberPreference(key = PreferencesKeys.TRACK_SORT, defaultValue = 0)

@Composable
fun rememberPlaylistSort() =
    rememberPreference(key = PreferencesKeys.PLAYLIST_SORT, defaultValue = 0)

@Composable
fun rememberArtistSort() =
    rememberPreference(key = PreferencesKeys.ARTIST_SORT, defaultValue = 0)

@Composable
fun rememberPauseOnMute() =
    rememberPreference(key = PreferencesKeys.PAUSE_ON_MUTE, defaultValue = false)

@Composable
fun rememberUseExpressivePalette() =
    rememberPreference(key = PreferencesKeys.USE_EXPRESSIVE_PALETTE, defaultValue = false)

@Composable
fun rememberMinTrackDuration() =
    rememberPreference(key = PreferencesKeys.MIN_TRACK_DURATION, defaultValue = 0)

@Composable
fun rememberWhitelistedFolders() =
    rememberPreference(key = PreferencesKeys.WHITELISTED_FOLDERS, defaultValue = emptySet())

@Composable
fun rememberHasBeenThroughSetup() =
    rememberPreference(key = PreferencesKeys.HAS_BEEN_THROUGH_SETUP, defaultValue = false)

@Composable
fun rememberHasSeenTip() =
    rememberPreference(key = PreferencesKeys.HAS_SEEN_TIP, defaultValue = false)

@Composable
fun rememberSortTracksAscending() =
    rememberPreference(key = PreferencesKeys.SORT_TRACKS_ASCENDING, defaultValue = true)

@Composable
fun rememberPlayerVolume() =
    rememberPreference(key = PreferencesKeys.PLAYER_VOLUME, defaultValue = 100)

@Composable
fun rememberMainScreenUseGrid() =
    rememberPreference(key = PreferencesKeys.MAIN_SCREEN_USE_GRID, defaultValue = true)

@Composable
fun rememberAlbumsUseGrid() =
    rememberPreference(key = PreferencesKeys.ALBUMS_USE_GRID, defaultValue = true)

@Composable
fun rememberPlaylistsUseGrid() =
    rememberPreference(key = PreferencesKeys.PLAYLISTS_USE_GRID, defaultValue = false)

@Composable
fun rememberArtistUseGrid() =
    rememberPreference(key = PreferencesKeys.ARTIST_USE_GRID, defaultValue = false)

@Composable
fun rememberListItemSize() =
    rememberPreference(key = PreferencesKeys.LIST_ITEM_SIZE, defaultValue = 60)

suspend fun getPauseOnMute(context: Context) =
    getPreference(key = PreferencesKeys.PAUSE_ON_MUTE, defaultValue = false, context = context)

fun getSafTracks(context: Context) =
    getPreferenceFlow(key = PreferencesKeys.SAF_TRACKS, defaultValue = emptySet(), context = context)


suspend fun getMinTrackDuration(context: Context) =
    getPreference(key = PreferencesKeys.MIN_TRACK_DURATION, defaultValue = 0, context = context)

suspend fun getWhitelistedFolders(context: Context) =
    getPreference(key = PreferencesKeys.WHITELISTED_FOLDERS, defaultValue = emptySet(), context = context)

suspend fun getPlayerVolume(context: Context) =
    getPreference(key = PreferencesKeys.PLAYER_VOLUME, defaultValue = 100, context = context)

suspend fun savePlayerVolume(context: Context, volume: Int) =
    savePreference(key = PreferencesKeys.PLAYER_VOLUME, value = volume, context = context)


//suspend fun saveMediaIndexToMediaIdMap(pair: LastPlayed, context: Context) =
//    saveCustomPreference(value = pair, key = MEDIA_INDEX_TO_MEDIA_ID, context = context)

//suspend fun getMediaIndexToMediaIdMap(context: Context) =
//    getCustomPreference(
//        key = MEDIA_INDEX_TO_MEDIA_ID,
//        defaultValue = LastPlayed("", 0L),
//        context = context
//    )



