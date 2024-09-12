package com.example.weatherforecast.ui.theme.View.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.weatherforecast.ui.theme.View.MyViewModel
import com.example.weatherforecast.ui.theme.retrofit.Api
import com.example.weatherforecast.ui.theme.retrofit.pojo.PojoData
import com.example.weatherforecast.ui.theme.View.interfaces.FragmentMethods
import com.example.weatherforecast.ui.theme.View.StyleUI
import com.example.weatherforecast.ui.theme.View.uiCompose.UiFragmentCityWeather
import com.example.weatherforecast.ui.theme.View.interfaces.UiInterface
import com.example.weatherforecast.ui.theme.View.uiCompose.UiFragmentChooseCity
import com.example.weatherforecast.ui.theme.View.uiCompose.UiProgressCircularAnimation

class FragmentCityWeather(private val uiInterface: UiInterface) : FragmentMethods {
    private var enable by mutableStateOf(true)
//    private var pojo by mutableStateOf(PojoData())
//    fun setPojoData(newPojo: PojoData) { // передача pojo данных после их получения
//        pojo = newPojo
//    }

    @Composable
    override fun run(
        styleUI: StyleUI,
        navController: NavHostController,
        api: Api,
        viewModel: MyViewModel
    ) {
        val uiProgressCircularAnimation =
            UiProgressCircularAnimation()
        val uiFragmentCityWeather =
            UiFragmentCityWeather(
                this@FragmentCityWeather, uiInterface,
                navController, api
            )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF8197dc)),
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState(initial = 0))
                    .background(Color(0xFF8197dc)),
            ) {
                uiFragmentCityWeather.setPojoData(viewModel.pojo.value ?: run { PojoData() })
                uiFragmentCityWeather.panelTopLabel(styleUI, enable)
                uiFragmentCityWeather.panelCenterLabel(styleUI, enable)
                uiFragmentCityWeather.panelBottomLabel(styleUI, enable)
            }
            uiProgressCircularAnimation.progressCircularAnimation(!enable)
        }
    }


    override fun enable(enable: Boolean) {
        this.enable = enable
    }
}