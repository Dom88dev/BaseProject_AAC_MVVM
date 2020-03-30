package com.project.base.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.base.model.local.dao.WeatherDao
import com.project.base.model.local.entity.Weather
import com.project.base.model.remote.api.WeatherAPI
import com.project.base.model.remote.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRepository(private val weatherApi: WeatherAPI, private val weatherDao: WeatherDao) {

    /*
    모든 화면에서 공통된 데이터를 가지고 있도록 하기위한 repository 클래스 내의 liveData 프로퍼티
    (본 베이스 프로젝트는 하나의 화면이지만 두개 이상의 화면일때 앱전체에서 공통적으로 사용하는 데이터의 경우
    repository 클래스내에 가지고 있는것이 데이터 손실을 막을 수 있다..)
     */
    private val weather: MutableLiveData<Weather> = MutableLiveData()
    private val weatherHistory: MutableLiveData<List<Weather>> = MutableLiveData()
    private var weatherHistoryData: List<Weather> = arrayListOf()

    /*
    뷰모델에서 livedata 인스턴스를 받기위한 getter 메서드들
     */
    fun getWeatherLiveData(): MutableLiveData<Weather> {
        return weather
    }

    fun getWeatherHistoryLiveData(): MutableLiveData<List<Weather>> {
        return weatherHistory
    }

    /*
    api 통신을 통해 최신 날씨정보를 불러온다.
    불러온 정보를 weather 프로퍼티에 값을 저장하며 로컬 db에도 저장한 후 최신 날씨내역 목록도 갱신해준다.
     */
    suspend fun refreshWeather(cityName: String = "seoul") {

        //coroutines 를 이용해서 Call<>이 아닌 Response<>객체로 리턴을 받아 좀 더 간단하게 api를 처리하게 됨.
        val response = weatherApi.getByCityName(cityName)
        if (response.isSuccessful) {
            Log.d("RESULT", "weatherResponse : ${response.body()?.timeOfData}")
            weather.value = Weather.to(response.body()!!)
            Log.d("RESULT", "weather : ${weather.value!!.state}")
            insert(response.body()!!)
            refreshWeatherHistory()
        } else {
            Log.d("RESULT", "weatherResponse is fail : ${response.message()}}")
        }
    }

    /*
    날씨데이터를 로컬 db에 저장한다.
     */
    private suspend fun insert(weather: WeatherResponse) {
        withContext(Dispatchers.IO) { weatherDao.insert(Weather.to(weather)) }
    }


    /*
    최신 날씨 내역 데이터를 불러들인다.
    room은 백그라운드에서만 액세스 가능하고 liveData의 value는 메인쓰레드에서만 변경이 가능하기때문에
    weatherHistoryData로 데이터를 백그라운드에서 받은 후 메인쓰레드에서 liveData의 value값을 변경해준다.
     */
    private suspend fun refreshWeatherHistory() {
        withContext(Dispatchers.IO) {
            weatherHistoryData = weatherDao.getWeatherHistoryList()
            Log.d("RESULT", "history data is null or empty? ${weatherHistoryData.isNullOrEmpty()}")
        }
        weatherHistory.value = weatherHistoryData
    }

    suspend fun switchWeathers(weather1: Weather, weather2: Weather) {
        val id1 = weather1.id
        weather1.id = weather2.id
        weather2.id = id1
        withContext(Dispatchers.IO) {
            weatherDao.update(weather1, weather2)
        }
    }

    suspend fun removeWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            weatherDao.delete(weather)
        }
    }
}