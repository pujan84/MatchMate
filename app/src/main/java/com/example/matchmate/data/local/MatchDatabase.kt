package com.example.matchmate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MatchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MatchDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    companion object {

        @Volatile
        private var INSTANCE: MatchDatabase? = null

        fun getDatabase(context: Context): MatchDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchDatabase::class.java,
                    "matchmate_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}