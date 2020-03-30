package com.project.base.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.project.base.R
import com.project.base.databinding.FragmentRecentWeatherBinding
import com.project.base.model.local.entity.Weather
import com.project.base.ui.BindingFragment
import com.project.base.viewmodel.MainViewModel

class RecentWeatherFragment : BindingFragment<FragmentRecentWeatherBinding>() {

    override fun getLayoutResId() = R.layout.fragment_recent_weather

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //액티비티의 뷰모델을 가져온다 getViewModel()로 가져올 경우 새로운 인스턴스가 생성됨..
        binding.vm = (activity as MainActivity).mainViewModel
        binding.weatherNow = (binding.vm as MainViewModel).weather.value
        binding.lifecycleOwner = this

        /*
        뷰모델내의 weather 라이브데이터를 관찰한다.
        값이 변경되는 경우 databinding의 variable로 빼둔 weatherNow를 갱신해줌으로 ui를 변경시킨다.
         */
        binding.vm!!.weather.observe(viewLifecycleOwner,
            Observer<Weather> {
                binding.weatherNow = it
            })
    }

}
