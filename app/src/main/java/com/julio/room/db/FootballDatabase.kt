package com.julio.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.julio.room.entity.Team

@Database(entities = [Team::class], version = 1)
abstract class FootballDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

    companion object {
        private var INSTANCE: FootballDatabase? = null

        fun createDatabase(context: Context): FootballDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FootballDatabase::class.java, "db_football")
                        .allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}