package com.thienphu.mytodolistapp.navigation

import androidx.navigation.NavHostController
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.Constants.LIST_SCREEN


class Screens (navController : NavHostController) {
    val list : (Action) -> Unit = {
        action ->
        navController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN){inclusive = true}
        }
    }

    val task : (Int) -> Unit = {
        taskId -> navController.navigate("task/$taskId")
    }


}