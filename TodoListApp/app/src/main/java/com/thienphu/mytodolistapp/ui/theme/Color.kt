package com.thienphu.mytodolistapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val lowPriorityColor = Color(0xFF00C980)
val mediumPriorityColor = Color(0xFFFFC114)
val highPriorityColor = Color(0xFFFF4646)
val nonePriorityColor = Color(0xFFFFFFFF)

val Colors.topAppBarColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray
