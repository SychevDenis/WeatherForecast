package com.example.weatherforecast.ui.theme.View.uiCompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.weatherforecast.R
import com.example.weatherforecast.ui.theme.View.StyleUI
import com.example.weatherforecast.ui.theme.retrofit.Api
import com.example.weatherforecast.ui.theme.retrofit.pojo.PojoData
import com.example.weatherforecast.ui.theme.View.interfaces.FragmentMethods
import com.example.weatherforecast.ui.theme.View.interfaces.UiInterface

class UiFragmentCityWeather(
    private val fragment: FragmentMethods,
    private val activity: UiInterface,
    private val navController: NavHostController,
    private val api: Api,
) {
    private var pojo by mutableStateOf(PojoData())
    fun setPojoData(newPojo: PojoData) { // передача pojo данных после их получения
        pojo = newPojo
    }

    @Composable
    //панель с выводом региона, времени и кнопкой обновить
    fun panelTopLabel(styleUI: StyleUI, enable: Boolean) {
        Card(
            modifier = Modifier
                .padding(
                    styleUI.distanceLeftRight, styleUI.distanceTopBottom / 2,
                    styleUI.distanceLeftRight, 0.dp
                ),
            shape = RoundedCornerShape(StyleUI.roundedCornerShape)
        ) {
            Row(
                modifier = Modifier
                    .background(styleUI.colorBackgroundCard)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Выравниваем элементы по краям
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(start = styleUI.distanceLeftRight / 3)) {
                    Text(
                        modifier = Modifier.padding(
                            styleUI.distanceLeftRight / 2,
                            styleUI.distanceLeftRight / 2,
                            0.dp,
                            0.dp
                        ), text = "Регион: ${pojo.location?.region}",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.padding(
                            styleUI.distanceLeftRight / 2,
                            styleUI.distanceLeftRight / 6,
                            0.dp,
                            styleUI.distanceLeftRight / 2
                        ), text = "${pojo.location?.localtime}",
                        fontSize = styleUI.fontSizeLittle,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText1,
                        textAlign = TextAlign.Start
                    )
                }
                Button(
                    enabled = enable,
                    onClick = {
                        pojo.location?.name?.let {
                            activity.searchWeatherCity(
                                city = it,
                                // city = "Null",
                                navController = navController,
                                api = api,
                                fragment = fragment,
                                "fragmentCityWeather"
                            )
                        }
                    },
                    modifier = Modifier.padding(end = styleUI.distanceLeftRight / 3), // Добавляем отступ справа
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = "Обновить",
                        modifier = Modifier.size(36.dp),
                    )
                }
            }
        }
    }

    @Composable
    //панель с картинкой погоды
    fun panelCenterLabel(styleUI: StyleUI, enable: Boolean) {
        Card(
            modifier = Modifier
                .padding(
                    styleUI.distanceLeftRight, styleUI.distanceTopBottom / 2,
                    styleUI.distanceLeftRight, 0.dp
                ),
            shape = RoundedCornerShape(styleUI.roundedCornerShape)
        ) {
            Column(
                modifier = Modifier
                    .background(styleUI.colorBackgroundCard)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    modifier = Modifier.padding(
                        top = styleUI.distanceTopBottom / 2,
                    ),
                    text = pojo.current?.weatherDescriptions?.first().toString(),
                    fontSize = styleUI.fontSizeBig,
                    fontWeight = styleUI.fontWeight,
                    color = styleUI.colorText2,
                )
                pojo.current?.weatherIcons?.let { WeatherImage(styleUI, it.last()) }
                Text(
                    text = "${pojo.current?.temperature}°",
                    fontSize = styleUI.fontSizeBig * 3,
                    fontWeight = styleUI.fontWeight,
                    color = styleUI.colorText2,
                )
                Text(
                    text = "Ощущается как ${pojo.current?.feelslike}°",
                    fontSize = styleUI.fontSizeBig,
                    fontWeight = styleUI.fontWeight,
                    color = styleUI.colorText2,
                )
                var nameCity = "null"
                pojo.location?.name?.let { nameCity = it }
                Text(
                    modifier = Modifier.padding(
                        bottom = styleUI.distanceTopBottom / 2,
                    ),
                    text = nameCity,
                    fontSize = when (nameCity.length) {
                        in 0..9 -> {
                            styleUI.fontSizeBig * 3
                        }

                        in 10..13 -> {
                            styleUI.fontSizeBig * 2
                        }

                        else -> {
                            styleUI.fontSizeBig * 1.3
                        }
                    },
                    fontWeight = styleUI.fontWeight,
                    color = styleUI.colorText1,
                )
            }
        }
    }

    @Composable
    //нижняя панель погоды
    fun panelBottomLabel(styleUI: StyleUI, enable: Boolean) {
        Card(
            modifier = Modifier
                .padding(
                    styleUI.distanceLeftRight, styleUI.distanceTopBottom / 2,
                    styleUI.distanceLeftRight, 0.dp
                ),
            shape = RoundedCornerShape(styleUI.roundedCornerShape)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(styleUI.colorBackgroundCard)
                    .padding(
                        top = styleUI.distanceTopBottom / 2,
                        bottom = styleUI.distanceTopBottom / 2,
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Скорость ветра",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                    Text(
                        text = "Давление",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                    Text(
                        text = "Влажность",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                    Text(
                        text = "Осадки",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${pojo.current?.windSpeed} м/с",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                    Text(
                        text = "${pojo.current?.pressure} мм",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                    Text(
                        text = "${pojo.current?.humidity}%",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                    Text(
                        text = "${pojo.current?.precip} мм",
                        fontSize = styleUI.fontSizeBig,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText2,
                    )
                }
            }
        }

    }

    //функция загрузки изображения из интернета
    @Composable
    fun WeatherImage(styleUI: StyleUI, imageUrl: String) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Ясное небо ночью",
            modifier = Modifier
                .size(200.dp)
                .padding(top = styleUI.distanceTopBottom / 2)
        )
    }
}