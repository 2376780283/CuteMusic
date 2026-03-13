package com.zzh.cutemusic.di

import androidx.room.Room
import com.zzh.cutemusic.data.AbstractTracksScanner
import com.zzh.cutemusic.data.LyricsParser
import com.zzh.cutemusic.data.datastore.UserPreferences
import com.zzh.cutemusic.data.playlist.MIGRATION_1_2
import com.zzh.cutemusic.data.playlist.MIGRATION_2_3
import com.zzh.cutemusic.data.playlist.PlaylistDatabase
import com.zzh.cutemusic.domain.repository.AlbumsRepository
import com.zzh.cutemusic.domain.repository.ArtistsRepository
import com.zzh.cutemusic.domain.repository.FoldersRepository
import com.zzh.cutemusic.domain.repository.PlaybackStatsRepository
import com.zzh.cutemusic.domain.repository.PlaylistsRepository
import com.zzh.cutemusic.domain.repository.SafManager
import com.zzh.cutemusic.presentation.screens.album.AlbumDetailsViewModel
import com.zzh.cutemusic.presentation.screens.album.AlbumsViewModel
import com.zzh.cutemusic.presentation.screens.artist.ArtistDetailsViewModel
import com.zzh.cutemusic.presentation.screens.artist.ArtistsViewModel
import com.zzh.cutemusic.presentation.screens.lyrics.LyricsViewModel
import com.zzh.cutemusic.presentation.screens.main.MainViewModel
import com.zzh.cutemusic.presentation.screens.metadata.MetadataViewModel
import com.zzh.cutemusic.presentation.screens.playlists.PlaylistDetailsViewModel
import com.zzh.cutemusic.presentation.screens.playlists.PlaylistViewModel
import com.zzh.cutemusic.presentation.screens.quickplay.QuickPlayViewModel
import com.zzh.cutemusic.presentation.screens.settings.FoldersViewModel
import com.zzh.cutemusic.presentation.screens.settings.SafViewModel
import com.zzh.cutemusic.presentation.screens.stats.StatsViewModel
import com.zzh.cutemusic.presentation.shared_components.MusicViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = PlaylistDatabase::class.java,
            name = "playlist.db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
            .dao
    }



    singleOf(::AbstractTracksScanner)
    singleOf(::LyricsParser)
    singleOf(::FoldersRepository)
    singleOf(::SafManager)
    singleOf(::AlbumsRepository)
    singleOf(::ArtistsRepository)
    singleOf(::PlaylistsRepository)
    singleOf(::PlaybackStatsRepository)
    singleOf(::UserPreferences)
    viewModelOf(::MusicViewModel)
    viewModelOf(::MetadataViewModel)
    viewModelOf(::PlaylistViewModel)
    viewModelOf(::PlaylistDetailsViewModel)
    viewModelOf(::QuickPlayViewModel)
    viewModelOf(::ArtistsViewModel)
    viewModelOf(::ArtistDetailsViewModel)
    viewModelOf(::AlbumsViewModel)
    viewModelOf(::AlbumDetailsViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::FoldersViewModel)
    viewModelOf(::SafViewModel)
    viewModelOf(::LyricsViewModel)
    viewModelOf(::StatsViewModel)
}
