package com.example.weatherforecast.ui.theme.View.interfaces

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.weatherforecast.ui.theme.View.MyViewModel
import com.example.weatherforecast.ui.theme.retrofit.Api
import com.example.weatherforecast.ui.theme.View.StyleUI

interface FragmentMethods {
    fun enable(enable:Boolean)
    @Composable
    fun run(styleUI: StyleUI,
            navController: NavHostController,
            api: Api,
            viewModel: MyViewModel
    )
}