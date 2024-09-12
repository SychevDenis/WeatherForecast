package com.example.weatherforecast.ui.theme.View

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.ui.theme.retrofit.pojo.PojoData

class MyViewModel() : ViewModel() {
    var pojo = MutableLiveData(PojoData())
}