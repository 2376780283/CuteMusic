package com.zzh.cutemusic.data.playlist

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zzh.cutemusic.data.MediaItemConverter
import com.zzh.cutemusic.data.models.Playlist
import com.zzh.cutemusic.data.models.PlaybackStat

@Database(
    entities = [Playlist::class, PlaybackStat::class],
    version = 3
)
@TypeConverters(MediaItemConverter::class)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract val dao: PlaylistDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Playlist ADD COLUMN color INTEGER NOT NULL DEFAULT -1")
        db.execSQL("ALTER TABLE Playlist ADD COLUMN tags TEXT NOT NULL DEFAULT '[]'")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `PlaybackStat` (`mediaId` TEXT NOT NULL, `totalPlayTimeMs` INTEGER NOT NULL, `playCount` INTEGER NOT NULL, `lastPlayedTimestamp` INTEGER NOT NULL, PRIMARY KEY(`mediaId`))"
        )
    }
}