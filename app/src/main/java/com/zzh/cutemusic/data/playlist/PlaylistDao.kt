package com.zzh.cutemusic.data.playlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.zzh.cutemusic.data.models.Playlist
import com.zzh.cutemusic.data.models.PlaybackStat
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Upsert
    suspend fun upsertPlaylist(playlist: Playlist)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Query("SELECT * FROM playlist ORDER BY name ASC")
    fun getPlaylists(): Flow<List<Playlist>>

    @Query("SELECT * FROM playlist WHERE id = :id")
    fun getPlaylistDetails(id: Int): Flow<Playlist>

    @Upsert
    suspend fun upsertPlaybackStat(playbackStat: PlaybackStat)

    @Query("SELECT * FROM PlaybackStat WHERE mediaId = :mediaId")
    suspend fun getPlaybackStat(mediaId: String): PlaybackStat?

    @Query("SELECT * FROM PlaybackStat ORDER BY totalPlayTimeMs DESC")
    fun getTopPlayedTracks(): Flow<List<PlaybackStat>>
}