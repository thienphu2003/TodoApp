package com.thienphu.mytodolistapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.Constants.LIST_ARGUMENT_KEY
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN
import com.thienphu.mytodolistapp.utils.Constants.LIST_TASK
import com.thienphu.mytodolistapp.utils.Constants.TASK_ARGUMENT_KEY


fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
){
    composable(
        LIST_TASK,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) { type = NavType.IntType })
    ) {

    }
}