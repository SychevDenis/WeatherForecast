package com.example.weatherforecast.ui.theme.View.interfaces

import androidx.navigation.NavHostController
import com.example.weatherforecast.ui.theme.retrofit.Api

interface UiInterface {
    fun searchWeatherCity(
        city: String,
        navController: NavHostController,
        api: Api,
        fragment: FragmentMethods,
        fragmentName:String
    )
}