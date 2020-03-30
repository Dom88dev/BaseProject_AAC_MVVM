package com.project.base.ui.main


import android.os.Bundle
import com.project.base.R
import com.project.base.databinding.MainActivityBinding
import com.project.base.ui.BindingActivity
import com.project.base.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BindingActivity<MainActivityBinding>() {

    //액티비티 내의 프레그먼트와 하나의 뷰모델을 공유하기 위해 따로 프로퍼티를 선언.
    val mainViewModel: MainViewModel by viewModel()

    override fun getLayoutResId(): Int = R.layout.main_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = mainViewModel
    }

}
