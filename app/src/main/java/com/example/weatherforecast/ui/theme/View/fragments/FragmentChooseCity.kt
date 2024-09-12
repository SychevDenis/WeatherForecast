package com.example.weatherforecast.ui.theme.View.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.weatherforecast.ui.theme.View.MyViewModel
import com.example.weatherforecast.ui.theme.retrofit.Api
import com.example.weatherforecast.ui.theme.View.interfaces.FragmentMethods
import com.example.weatherforecast.ui.theme.View.StyleUI
import com.example.weatherforecast.ui.theme.View.uiCompose.UiFragmentChooseCity
import com.example.weatherforecast.ui.theme.View.interfaces.UiInterface
import com.example.weatherforecast.ui.theme.View.uiCompose.UiProgressCircularAnimation

class FragmentChooseCity(private val activity: UiInterface, private val listCity: List<String>) :
    FragmentMethods {
    private var enable by mutableStateOf(true)

    @Composable
    override fun run(
        styleUI: StyleUI,
        navController: NavHostController,
        api: Api,
        viewModel: MyViewModel
    ) {
        val uiProgressCircularAnimation =
            UiProgressCircularAnimation()
        val uiFragmentChooseCity =
            UiFragmentChooseCity(this@FragmentChooseCity, activity, navController, api)
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF8197dc)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
            )
            {
                uiFragmentChooseCity.panelWithCitySearch(styleUI, enable)
                uiFragmentChooseCity.panelWithCityButtons(listCity, styleUI, enable)
            }
            uiProgressCircularAnimation.progressCircularAnimation(!enable) // анимация загрузки
        }
    }

    override fun enable(enable: Boolean) {
        this.enable = enable
    }
}