package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.database.AsteroidDatabase
import com.udacity.asteroidradar.api.network.NasaService
import com.udacity.asteroidradar.api.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroids()

    suspend fun fetchAsteroids() {
        withContext(Dispatchers.IO) {

            val dateFormatter =
                SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val startDate = dateFormatter.format(System.currentTimeMillis())

            val asteroidsResult =
                NasaService.service.getAsteroidsJson(startDate, BuildConfig.API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsResult))

            database.asteroidDao.insertAsteroids(*asteroids.toTypedArray())
        }
    }

    suspend fun deleteAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deleteOldAsteroids()
        }
    }

    suspend fun loadPictureOfDay() = withContext(Dispatchers.IO) {
        val picture = NasaService.service.getPictureOfDay(BuildConfig.API_KEY)
        if (picture.mediaType == "image") {
            return@withContext picture
        } else {
            return@withContext null
        }
    }

}