package com.project.base.model.remote.api

import com.project.base.BaseApp
import com.project.base.model.remote.response.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    /*
    retrofit 2.6.0 버전 이전에는 Coroutines사용을 위해 coroutinesAdapter를 디펜던시에 추가하여 retrofit builder에 적용해준 후 Deferred 타입으로 리턴을 받도록 처리해야 했으나
    2.6.0으로 업데이트되면서 coroutines를 어댑터 추가없이 기존의 call<>을 사용하듯이 사용하면 되도로고 변경되었다.
    Response<>에 담지않고 바로 객체를 받아도 되지만 api통신에대한 결과와 통신 실패시 에러 처리를 위하여 Response<T>를 반환값으로 사용.
     */
    @GET("weather")
    suspend fun getByCityName(@Query("q") cityName: String, @Query("appid") apiKey: String = BaseApp.WEATHER_API_KEY, @Query("lang") lang: String = "kr"): Response<WeatherResponse>

    @GET("weather")
    fun getByCityId(@Query("id") cityId: String, @Query("appid") apiKey: String = BaseApp.WEATHER_API_KEY, @Query("lang") lang: String = "kr"): Response<WeatherResponse>

    @GET("weather")
    fun getByCoordinates(@Query("lat") lat: Long, @Query("lon") lon: Long, @Query("appid") apiKey: String = BaseApp.WEATHER_API_KEY, @Query("lang") lang: String = "kr"): Response<WeatherResponse>
}