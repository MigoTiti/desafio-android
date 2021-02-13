package com.lucasrodrigues.apodnasa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO

@Database(
    entities = [
        ApodDBO::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java, "apod_database.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun apodDao(): ApodDao
}