package com.project.base.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 데이터 바인딩뷰를 가진 데이터바인딩 액티비티의 슈퍼클래스
 * 이 클래스를 상속받아 getLayoutResId()에 layout의 id를 대입시키면
 * 기본적인 데이터바인딩 구현은 처리가 되며 onCreate()에서 각 액티비티에 추가되어 있는 데이터 값들을 구현해 주면 된다.
 */
abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutResId())
    }
}