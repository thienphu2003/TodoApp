package com.thienphu.mytodolistapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thienphu.mytodolistapp.ui.list.ListScreen
import com.thienphu.mytodolistapp.ui.theme.splash.SplashScreen
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Constants.LIST_ARGUMENT_KEY
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN
import com.thienphu.mytodolistapp.utils.Constants.SPLASH_SCREEN
import com.thienphu.mytodolistapp.utils.stringToAction

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit,
){
    composable(
        SPLASH_SCREEN,
    ) {
        SplashScreen(navigateToListScreen)
    }
}