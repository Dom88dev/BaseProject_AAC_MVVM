package com.project.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.project.base.model.local.entity.Weather
import com.project.base.repository.WeatherRepository
import com.project.base.ui.main.WeatherHistoryRecyclerViewAdapter
import kotlinx.coroutines.launch

class MainViewModel constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    /*
     SwipeRefreshLayout의 isRefresh 값에 지정해주기 위한 liveData로 각 task의 처리 시작과 끝에 값을 변경해주어
     SwipeRefreshLayout의 로딩 애니메이션이 나타나도록 해준다.
     실제 뷰모델 내부의 프로퍼티들을 외부에서 값을 변경하지 못하도록 _가 붙은 프로퍼티에 실제 값이 있도록하며
     _가 없는 프로퍼티로 접근 가능하도로고 처리해놓음
     */
    private val _refreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val refreshing: MutableLiveData<Boolean>
        get() = _refreshing

    /*
     RecentWeatherFragment에서 관찰되는 최근 날씨 정보 데이터
     뷰모델이 사라지면 현재 데이터도 소실되기에 실제 라이브데이터의 인스턴스는 WeatherRepository에서 만들어지고
     해당 인스턴스를 대입해준다.
     */
    private val _weather: MutableLiveData<Weather> = weatherRepository.getWeatherLiveData()
    val weather: MutableLiveData<Weather>
        get() = _weather

    /*
     WeatherHistoryFragment에서 관찰되는 날씨 내역들의 데이터로
     마찬가지로 실제 프로퍼티의 인스턴스는 Repository 객체에서 생성된다.
     */
    private val _weatherHistroy: MutableLiveData<List<Weather>> =
        weatherRepository.getWeatherHistoryLiveData()
    val weatherHistory: MutableLiveData<List<Weather>>
        get() = _weatherHistroy


    init {
        viewModelScope.launch {
            getCurrentWeather()
        }
    }


    //실행되면서 최신 날씨 정보를 가져오며 가져온 데이터를 로컬에 저장하고 현재 날씨와 날씨 내역 리스트를 갱신해준다.
    private suspend fun getCurrentWeather() {
        _refreshing.value = true
        weatherRepository.refreshWeather()
        _refreshing.value = false
    }


    /*
    SwipeRefreshLayout의 onRefresh 이벤트가 발생하면 처리되는 function으로 데이터 바인딩 펑션에서는 suspend 펑션을
    사용할 수 없기에 일반 펀션으로 선언하고 내부에서 뷰모델 스코프로 처리하였다.
    */
    fun refreshWeather() {
        viewModelScope.launch {
            weatherRepository.refreshWeather()
            _refreshing.value = false
        }
    }

    /*
        ItemTouchHelper를 이용해서 RecyclerView의 아이템을 상하로 드래그했을 경우 해야할 처리를 구현
     */
    fun onMoveItemInWeatherHistoryList(
        weather1: Weather,
        weather2: Weather
    ) {
        Log.d("ITEM_CALLBACK", "ON MOVE!!")
        viewModelScope.launch {
            weatherRepository.switchWeathers(weather1, weather2)
        }
    }

    /*
    ItemTouchHelper를 이용해서 RecyclerView의 아이템을 좌우로 swipe했을 경우 해야할 처리를 구현
     */
    fun onSwipeItemInWeatherHistoryList(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int,
        recyclerView: RecyclerView
    ) {
        Log.d("ITEM_CALLBACK", "ON SWIPE!!")
        val posSwipe = viewHolder.adapterPosition
        viewModelScope.launch {
            recyclerView.adapter?.run {
                if (this is WeatherHistoryRecyclerViewAdapter) {
                    val weather = this.getItemAtPosition(posSwipe)
                    if (weather != null) {
                        weatherRepository.removeWeather(weather)
                        this.removeItemAtPosition(posSwipe)
                    }
                }
            }
        }
    }

}
