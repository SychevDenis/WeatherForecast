package com.example.weatherforecast

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.ui.theme.View.MyViewModel
import com.example.weatherforecast.ui.theme.WeatherForecastTheme
import com.example.weatherforecast.ui.theme.retrofit.Api
import com.example.weatherforecast.ui.theme.View.interfaces.FragmentMethods
import com.example.weatherforecast.ui.theme.View.StyleUI
import com.example.weatherforecast.ui.theme.View.interfaces.UiInterface
import com.example.weatherforecast.ui.theme.View.fragments.FragmentChooseCity
import com.example.weatherforecast.ui.theme.View.fragments.FragmentCityWeather
import com.example.weatherforecast.ui.theme.retrofit.pojo.PojoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity(), UiInterface {
    private val viewModel: MyViewModel by
    lazy { ViewModelProvider(this)[MyViewModel::class.java] }
    private val listCityRu = setOf(
        "Москва", "Санкт Петербург", "Новосибирск",
        "Екатеринбург", "Казань", "Нижний Новгород", "Самара"
    ).toList()
    private val fragmentChooseCity = FragmentChooseCity(this@MainActivity, listCityRu)
    private val fragmentCityWeather = FragmentCityWeather(this@MainActivity)
    private val baseUrl = "https://api.weatherstack.com"
    private val key = "введите свой ключ"
    private val scope = CoroutineScope(Dispatchers.IO) // CoroutineScope для IO потока

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder().baseUrl(baseUrl) // объект ретрофит с baseUrl
            .addConverterFactory(GsonConverterFactory.create()).build() // добавляем парсер
        val api = retrofit.create(Api::class.java) // добавляем интерфейс API
        setContent {
            val navController = rememberNavController()
            WeatherForecastTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(navController, api)
                }
            }
        }
    }

    @Composable
    fun MyApp(navController: NavHostController, api: Api) {
        NavHost(navController = navController, startDestination = "fragmentChooseCity") {
            composable("fragmentChooseCity") {
                fragmentChooseCity.run(
                    styleUI = StyleUI,
                    navController = navController,
                    api = api,
                    viewModel = viewModel
                )
            }
            composable("fragmentCityWeather") {
                fragmentCityWeather.run(
                    styleUI = StyleUI,
                    navController = navController,
                    api = api,
                    viewModel = viewModel
                )
            }
        }
    }

    //функция перевода фрагмента в неактивный режим и выставление анимации загрузки
    private fun switchFragmentDownloadMode(fragment: FragmentMethods, enable: Boolean) {
        fragment.enable(enable = enable)
    }

    override fun searchWeatherCity( //функция вызывается из фрагментов
        city: String,
        navController: NavHostController,
        api: Api,
        fragment: FragmentMethods,
        fragmentName: String
    ) {
        switchFragmentDownloadMode(fragment, false) // неактивный режим у фрагмента
        getScopeMainActivity().launch {
            viewModel.pojo.postValue(api.findWeatherCity(city = city, key = getKey()))
            switchFragmentDownloadMode(fragment, true) // активный режим у фрагмента
            withContext(Dispatchers.Main) {
                viewModel.pojo.value?.let {
                    if (it.location?.region == null
                        || it.request == null
                    ) {
                        showToast(
                            this@MainActivity, text = "Ошибка, " +
                                    "проверьте наличие интернет соединения"
                        )
                    } else {
                        if (fragmentName == "fragmentChooseCity") {
                            navController.navigate("fragmentCityWeather")
                        } else if (fragmentName == "fragmentCityWeather") {
                            navController.popBackStack()
                            navController.navigate("fragmentCityWeather")
                        }
                    }
                }
            }
        }
    }

    private fun showToast(context: Context, text: String) { // показать Toast
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun getScopeMainActivity(): CoroutineScope { // получить CoroutineScope
        return scope
    }

    private fun getKey(): String { //прочитать ключ
        return key
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WeatherForecastTheme {
//
//    }
//}


