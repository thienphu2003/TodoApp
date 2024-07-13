package com.thienphu.mytodolistapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.thienphu.mytodolistapp.navigation.destinations.listComposable
import com.thienphu.mytodolistapp.navigation.destinations.taskComposable
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN


@Composable
fun SetUpNavigation(navController : NavHostController, sharedViewModel: SharedViewModel){

    val screen = remember(navController){
        Screens(navController)
    }

    NavHost(navController = navController, startDestination = LIST_SCREEN){
        listComposable(navigateToTaskScreen = screen.task,sharedViewModel)
        taskComposable(navigateToListScreen = screen.list)

    }

}