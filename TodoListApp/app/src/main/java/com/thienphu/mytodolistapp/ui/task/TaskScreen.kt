package com.thienphu.mytodolistapp.ui.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    val title : String by sharedViewModel.title
    val description :String by sharedViewModel.description
    val priority : Priority by sharedViewModel.priority

    Scaffold(topBar = {
        TaskAppBar(selectedTask, navigateToListScreen = navigateToListScreen)
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