package com.udacity.asteroidradar.api.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.models.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(vararg asteroids: Asteroid)

    @Query("DELETE from asteroid_data WHERE closeApproachDate < strftime('%Y-%m-%d', 'now')")
    suspend fun deleteOldAsteroids()

    @Query("SELECT * FROM asteroid_data WHERE closeApproachDate >= strftime('%Y-%m-%d', 'now') ORDER BY closeApproachDate ASC")
    fun getAsteroids(): LiveData<List<Asteroid>>

}