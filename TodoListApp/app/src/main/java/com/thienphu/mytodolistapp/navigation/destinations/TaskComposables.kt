package com.thienphu.mytodolistapp.navigation.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thienphu.mytodolistapp.ui.task.TaskScreen
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.Constants.LIST_ARGUMENT_KEY
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN
import com.thienphu.mytodolistapp.utils.Constants.LIST_TASK
import com.thienphu.mytodolistapp.utils.Constants.TASK_ARGUMENT_KEY


fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
){
    composable(
        LIST_TASK,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) { type = NavType.IntType })
    ) {
        navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId)
        val selectedTask by sharedViewModel.selected_task.collectAsState()
        LaunchedEffect(key1= selectedTask) {
            Log.d("Selected task", "$selectedTask")
            if(selectedTask != null || taskId == -1 ){
                sharedViewModel.updateTaskFields(toDoTask = selectedTask)
            }

        }
        TaskScreen(selectedTask,navigateToListScreen = navigateToListScreen,sharedViewModel)
    }
}