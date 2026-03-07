@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.zzh.cutemusic.utils

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavKey
import com.zzh.cutemusic.presentation.navigation.Screen

val LocalSharedTransitionScope =
    compositionLocalOf<SharedTransitionScope> { throw IllegalStateException("No SharedTransitionScope provided") }
val LocalScreen = compositionLocalOf<NavKey> { Screen.Main }