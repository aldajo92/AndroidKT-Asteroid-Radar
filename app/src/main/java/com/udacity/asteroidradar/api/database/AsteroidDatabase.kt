package com.udacity.asteroidradar.api.database

import android.content.Context
import androidx.room.*
import com.udacity.asteroidradar.models.Asteroid

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(context, AsteroidDatabase::class.java, "asteroid_database")
                    .build()
        }
        return INSTANCE
    }
}



