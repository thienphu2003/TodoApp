package com.thienphu.mytodolistapp.navigation.destinations


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.navArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.thienphu.mytodolistapp.ui.list.ListScreen
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Constants.LIST_ARGUMENT_KEY
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN


fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId :Int) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) { type = NavType.StringType })
    ) {
       ListScreen(navigateToTaskScreen = navigateToTaskScreen,sharedViewModel)

    }
}