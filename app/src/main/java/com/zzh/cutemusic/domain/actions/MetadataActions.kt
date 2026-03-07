package com.zzh.cutemusic.domain.actions

import android.net.Uri

sealed interface MetadataActions {

    data class UpdateAudioArt(
        val newArtUri: Uri
    ) : MetadataActions

    data object SaveChanges : MetadataActions

    data object RemoveArtwork : MetadataActions

}