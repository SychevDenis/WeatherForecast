package com.example.weatherforecast.ui.theme.View.uiCompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class UiProgressCircularAnimation {
    @Composable
    fun progressCircularAnimation(enable:Boolean) {
        var downloadProgress by remember { mutableStateOf(value = 0.00f) }
        var directionRotation by remember { mutableStateOf(value = "clockwise") }
        var rotate by remember { mutableStateOf(value = 0f) }
        if (enable) {
            LaunchedEffect(downloadProgress) {
                if (directionRotation == "clockwise") {
                    while (downloadProgress < 1f) {
                        delay(8L)
                        rotate += 4f
                        downloadProgress += 0.02f
                    }
                    directionRotation = "counterclockwise"
                } else {
                    while (downloadProgress > 0f) {
                        delay(8L)
                        rotate += 10f
                        downloadProgress -= 0.02f
                    }
                    directionRotation = "clockwise"
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    progress = downloadProgress, // Устанавливаем прогресс
                    modifier = Modifier
                        .rotate(rotate)
                        .align(Alignment.Center) // Выравнивание по центру
                        .size(70.dp), // Размер индикатора
                    strokeWidth = 10.dp, // Задаем толщину индикатора
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}