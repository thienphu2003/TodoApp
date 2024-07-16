package com.thienphu.mytodolistapp.ui.task

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.thienphu.mytodolistapp.model.Priority
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Action


@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    BackHandler {
        navigateToListScreen(Action.NO_ACTION)
    }
    val title : String by sharedViewModel.title
    val description :String by sharedViewModel.description
    val priority : Priority by sharedViewModel.priority
    val context = LocalContext.current

    Scaffold(topBar = {
        TaskAppBar(selectedTask, navigateToListScreen = { action: Action ->
            if(action === Action.NO_ACTION){
                navigateToListScreen(action)
            }else {
                if(sharedViewModel.validateFields()){
                    navigateToListScreen(action)
                }else {
                    Toast.makeText(context,"Fields are empty",Toast.LENGTH_SHORT).show()
                }
            }
        })
    },
        content = { padding ->
            TaskContent(
                modifier = Modifier.padding(padding),
                title = title,
                onTitleChange = {
                   sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}

//@Composable
//fun BackHandler(
//    backPressedDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
//    onBackPressed: () -> Unit
//) {
//    val currrentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
//    val backCallback = remember {
//        object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                currrentOnBackPressed()
//                Log.d("Back pressed", "Trigger")
//            }
//        }
//    }
//
//    DisposableEffect(key1= backPressedDispatcher) {
//        backPressedDispatcher?.addCallback(backCallback)
//        onDispose {
//            backCallback.remove()
//        }
//    }
//}