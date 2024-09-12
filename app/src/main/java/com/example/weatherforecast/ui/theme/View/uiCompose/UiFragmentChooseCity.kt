package com.example.weatherforecast.ui.theme.View.uiCompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherforecast.ui.theme.View.StyleUI
import com.example.weatherforecast.ui.theme.retrofit.Api
import com.example.weatherforecast.ui.theme.View.interfaces.FragmentMethods
import com.example.weatherforecast.ui.theme.View.interfaces.UiInterface

class UiFragmentChooseCity(
    private val fragment: FragmentMethods,
    private val activity: UiInterface,
    private val navController: NavHostController,
    private val api: Api,
) {
    @Composable
    //панель ввода города с кнопкой поиска
    fun panelWithCitySearch(styleUI: StyleUI, enable: Boolean) {
        Row(
            modifier = Modifier
                .padding(16.dp, 25.dp, 16.dp, 0.dp)
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var text by rememberSaveable { mutableStateOf("") }
            //сделать отступы в первом и последнем элементе
            Button(enabled = enable,
                modifier = Modifier
                    .padding(styleUI.distanceLeftRight, 0.dp, 0.dp, 0.dp)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(styleUI.roundedCornerShape), // Скругленные углы
                colors = ButtonDefaults.buttonColors(
                    contentColor = styleUI.colorText1, // Цвет текста активной кноки
                    disabledContentColor = Color(0xFF7f7f7f),// Цвет текста неактивной кноки
                    disabledContainerColor = Color(0xFFc9c9c9),//цвет неактивной кноки
                    containerColor = styleUI.backgroundEnableColorButtonAndTextField, //цвет активной кноки
                ),
                onClick = {
                    activity.searchWeatherCity(
                        text,
                        navController,
                        api,
                        fragment,
                        "fragmentChooseCity"
                    )
                }) {
                Text(
                    modifier = Modifier
                        .padding(0.dp, 4.dp, 0.dp, 4.dp),
                    text = "Найти", // Текст на кнопке
                    fontSize = styleUI.fontSizeStandard,
                    fontWeight = styleUI.fontWeight,
                    color = styleUI.colorText1,
                    textAlign = TextAlign.Start
                )
            }
            Card(
                modifier = Modifier.padding(
                    styleUI.distanceLeftRight, 0.dp,
                    styleUI.distanceLeftRight, 0.dp
                ),
                shape = RoundedCornerShape(styleUI.roundedCornerShape) // Скругленные углы
            )
            {
                OutlinedTextField(
                    enabled = enable,
                    value = text,
                    onValueChange = { newValue ->
                        text = if (newValue.isEmpty()) {
                            newValue // Если пусто, не меняем
                        } else {
                            newValue.replaceFirstChar { it.uppercase() } // Первая буква заглавная
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (enable) styleUI.backgroundEnableColorButtonAndTextField
                            else styleUI.backgroundDisableColorButtonAndTextField
                        ),
                    placeholder = {
                        Text(
                            text = "Введите город",
                            maxLines = 1,
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = styleUI.fontSizeStandard,
                        fontWeight = styleUI.fontWeight,
                        color = styleUI.colorText1,
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,//Делаем цвет обводки прозрачным в фокусе
                        unfocusedBorderColor = Color.Transparent,// и вне фокуса
                    )
                )
            }
        }
    }

    @Composable
    //панель с кнопками городов
    fun panelWithCityButtons(listCity: List<String>, styleUI: StyleUI, enable: Boolean) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(styleUI.roundedCornerShape),
            //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // тень
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(styleUI.colorBackgroundCard)
                    .padding(vertical = styleUI.distanceTopBottom),// Отступы сверху и снизу
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(styleUI.distanceTopBottom) // Отступы между элементами
            ) {
                items(listCity) { city ->
                    Button(enabled = enable,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = styleUI.distanceLeftRight
                            ),
                        shape = RoundedCornerShape(styleUI.roundedCornerShape),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = styleUI.colorText1,
                            disabledContentColor = Color(0xFF7f7f7f),
                            disabledContainerColor = Color(0xFFc9c9c9),
                            containerColor = styleUI.backgroundEnableColorButtonAndTextField,
                        ),
                        onClick = {
                            activity.searchWeatherCity(
                                city,
                                navController,
                                api,
                                fragment,
                                "fragmentChooseCity"
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            text = city,
                            fontSize = styleUI.fontSizeStandard,
                            fontWeight = styleUI.fontWeight,
                            color = styleUI.colorText1,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}