package com.zzh.cutemusic.presentation.screens.main.components

import androidx.annotation.DrawableRes
import com.zzh.cutemusic.R
import com.zzh.cutemusic.presentation.navigation.Screen

data class MainScreenCategory(
    val title: String,
    @DrawableRes val icon: Int,
    val screen: Screen
)

val mainScreenCategories = listOf(
    MainScreenCategory(
        title = "专辑",
        icon = R.drawable.album_filled,
        screen = Screen.Albums
    ),
    MainScreenCategory(
        title = "艺术家",
        icon = R.drawable.artist_rounded,
        screen = Screen.Artists
    ),
    MainScreenCategory(
        title = "播放列表",
        icon = R.drawable.playlist,
        screen = Screen.Playlists
    )
)