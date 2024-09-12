package com.example.weatherforecast.ui.theme.retrofit.pojo

import com.google.gson.annotations.SerializedName

data class PojoData(
    @SerializedName("request") var request: Request? = Request(),
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("current") var current: Current? = Current()
)