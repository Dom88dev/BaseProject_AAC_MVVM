package com.project.base.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.project.base.model.local.converter.DateConverter
import com.project.base.model.remote.response.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

/**
 * room db에 저장되는 데이터 객체로 db의 테이블과 같이 구성하여 사용..
 */
@Entity(tableName = "weather_history")
@TypeConverters(DateConverter::class)
data class Weather(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "date_time") val dateTime: Date,
    @ColumnInfo(name = "weather_state") val state: String,
    @ColumnInfo(name = "weather_icon") val icon: String,
    @ColumnInfo(name = "min_temp") val min: Float,
    @ColumnInfo(name = "max_temp") val max: Float,
    @ColumnInfo(name = "humidity") val humid: Int,
    @ColumnInfo(name = "now_temp") val now: Float,
    @ColumnInfo(name = "feels_like_temp") val feelsLike: Float,
    @ColumnInfo(name = "city") val city: String
) {
    constructor() : this(
        dateTime = Date(),
        state = "",
        icon = "01d",
        min = 0f,
        max = 0f,
        humid = 0,
        now = 0f,
        feelsLike = 0f,
        city = "unknown"
    )

    fun getIconUri(): String {
        return "http://openweathermap.org/img/wn/$icon@2x.png"
    }

    fun getDateFormat(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)
        return simpleDateFormat.format(dateTime)
    }

    companion object {
        //api 통신으로 받는 리스폰스 객체에서 실제 앱내에서 사용하는 데이터 클래스 객체로 전환해주는 메소드
        fun to(weatherResponse: WeatherResponse): Weather {
            return Weather(
                dateTime = Date(),
                state = weatherResponse.weathers[0].weatherDescription,
                icon = weatherResponse.weathers[0].weatherIcon,
                min = toCelsius(weatherResponse.main.minTemp),
                max = toCelsius(weatherResponse.main.maxTemp),
                humid = weatherResponse.main.humid,
                now = toCelsius(weatherResponse.main.temperature),
                feelsLike = toCelsius(weatherResponse.main.feelTemp),
                city = weatherResponse.cityName
            )
        }

        //kelvin값을 celsius 값으로..
        private fun toCelsius(value: Float): Float {
            return value - 273.15f
        }
    }
}