package com.project.base.ui.binding_adpters.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.project.base.model.local.entity.Weather
import com.project.base.ui.main.WeatherHistoryRecyclerViewAdapter
import com.project.base.viewmodel.MainViewModel

/*
 리스트에 표시할 weather 객체의 List를 리싸이클러뷰의 어댑터에 세팅하고 리스트를 새로고침핸다.
 어댑터가 없으면 WeatherHistoryAdapter를 생성해서 지정해 준다.
 */
@BindingAdapter(value = ["weathers", "viewModel"])
fun setWeatherListItems(view: RecyclerView, items: List<Weather>?, vm: MainViewModel) {
    view.adapter?.run {
        if (this is WeatherHistoryRecyclerViewAdapter) {
            if (!items.isNullOrEmpty()) this.setValues(items)
        }
    } ?: run {
        WeatherHistoryRecyclerViewAdapter(vm).apply { view.adapter = this }
    }
}


@BindingAdapter("viewModelForItemTouchHelper")
fun setItemTouchHelper(view: RecyclerView, vm: MainViewModel) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPos = viewHolder.adapterPosition
            val toPos = target.adapterPosition
            if (fromPos != toPos) {
                view.adapter?.run {
                    if (this is WeatherHistoryRecyclerViewAdapter) {
                        this.rearrangeItems(fromPos, toPos)
                    }
                }
                return true
            }
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            vm.onSwipeItemInWeatherHistoryList(viewHolder, direction, view)
        }
    }).attachToRecyclerView(view)
}