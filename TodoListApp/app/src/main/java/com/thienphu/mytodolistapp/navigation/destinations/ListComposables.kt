package com.thienphu.mytodolistapp.navigation.destinations


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.navArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.thienphu.mytodolistapp.ui.list.ListScreen
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Constants.LIST_ARGUMENT_KEY
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN
import com.thienphu.mytodolistapp.utils.stringToAction


fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId :Int) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) { type = NavType.StringType })
    ) {
        val string = it.arguments?.getString(LIST_ARGUMENT_KEY)
        val action = stringToAction(string)
        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }
       ListScreen(navigateToTaskScreen = navigateToTaskScreen,sharedViewModel)

    }
}