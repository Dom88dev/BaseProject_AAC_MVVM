package com.project.base.model.local.dao

import androidx.room.*
import com.project.base.model.local.entity.Weather
import java.util.*

/**
 * room db에 접근하여 쿼리를 실행하기 위한 data access object
 */
@Dao
interface WeatherDao {

    @Query("select * from weather_history order by id DESC")
    suspend fun getWeatherHistoryList(): List<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg weather: Weather)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)

    @Query("delete from weather_history")
    suspend fun deleteAll()
}