package com.project.base.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.project.base.R
import com.project.base.databinding.FragmentWeatherHistoryListBinding
import com.project.base.model.local.entity.Weather
import com.project.base.ui.BindingFragment
import com.project.base.viewmodel.MainViewModel

import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *
 */
class WeatherHistoryFragment : BindingFragment<FragmentWeatherHistoryListBinding>() {

    override fun getLayoutResId() = R.layout.fragment_weather_history_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //액티비티의 뷰모델을 가져온다 getViewModel()로 가져올 경우 새로운 인스턴스가 생성됨..
        binding.vm = (activity as MainActivity).mainViewModel
        binding.items = (binding.vm as MainViewModel).weatherHistory.value
        binding.lifecycleOwner = this

        /*
        뷰모델내의 weatherHistory 리이브데이터의 변경을 관찰하며
        변경시 databinding variable인 items를 갱신시켜준다.
        (items의 변경으로 리스트가 자동 갱신된다.)
         */
        binding.vm!!.weatherHistory.observe(viewLifecycleOwner,
            Observer<List<Weather>> {
                binding.items = it
            })
    }

}
