package com.project.base.ui.main


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.base.R
import com.project.base.databinding.ItemWeatherHistoryBinding
import com.project.base.model.local.entity.Weather
import com.project.base.ui.BindingViewHolder
import com.project.base.viewmodel.MainViewModel

/**
 *
 */
class WeatherHistoryRecyclerViewAdapter(val vm: MainViewModel) :
    RecyclerView.Adapter<WeatherHistoryRecyclerViewAdapter.WeatherHistoryViewHolder>() {

    /*
    swipe 및 아이템 순서 재배열(드래그로 인한) 시 자연스러운 애니메이션 효과를 위해 뷰모델에 있는 리스트의 인스턴스를 그대로 쓰지않고 MutableList로 생성하여 사용
     */
    private var items: MutableList<Weather> =
        if (vm.weatherHistory.value.isNullOrEmpty()) mutableListOf() else vm.weatherHistory.value!!.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_history, parent, false)
        return WeatherHistoryViewHolder(view)
    }

    /*
    데이터 바인딩 variable들을 대입시켜준다.
     */
    override fun onBindViewHolder(holder: WeatherHistoryViewHolder, position: Int) {
        holder.binding.vm = vm
        holder.binding.item = items[position]
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

    /*
    데이터 값을 변경한 후 리스트를 갱신하도록 한다.
     */
    fun setValues(values: List<Weather>) {
        items = values.toMutableList()
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Weather? {
        return items[position]
    }

    fun removeItemAtPosition(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    /*
    드래그로 인한 아이템 순서 재배열 시 스크롤링 후에도 재배열한 순서가 유지되도록 items를 변경 후
    뷰모델의 메소드를 호출해 실제 room 내의 데이터도 변경시켜준다.
     */
    fun rearrangeItems(fromPos: Int, toPos: Int) {
        Log.d("ITEM_CALLBACK", "ON MOVE START!! $fromPos / $toPos")
        val weather = items[fromPos]
        val toWeather = items[toPos]
        if (fromPos > toPos) {
            items.removeAt(fromPos)
            items.add(toPos, weather)
        } else {
            items.add(toPos, weather)
            items.removeAt(fromPos)
        }
        notifyItemMoved(fromPos, toPos)
        vm.onMoveItemInWeatherHistoryList(weather, toWeather)
        Log.d("ITEM_CALLBACK", "ON MOVE END!! $fromPos / $toPos")
    }

    inner class WeatherHistoryViewHolder(view: View) :
        BindingViewHolder<ItemWeatherHistoryBinding>(view)
}
