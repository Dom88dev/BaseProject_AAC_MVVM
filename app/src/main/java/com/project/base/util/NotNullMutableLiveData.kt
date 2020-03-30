package com.project.base.util

import androidx.lifecycle.MutableLiveData

/*
본 프로젝트에서 사용되진 않았지만 특정 ui 및 구조에서 사용되어 질 수도 있기에 구현..
 */
class NotNullMutableLiveData<T : Any>(defaultValue: T) : MutableLiveData<T>() {

    init {
        value = defaultValue
    }

    override fun getValue()  = super.getValue()!!
}