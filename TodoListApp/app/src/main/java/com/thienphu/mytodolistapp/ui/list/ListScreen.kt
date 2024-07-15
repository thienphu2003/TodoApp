package com.thienphu.mytodolistapp.ui.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thienphu.mytodolistapp.R
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.ui.theme.Teal200
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.RequestState
import com.thienphu.mytodolistapp.utils.SearchAppBarState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchTasks by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val message by sharedViewModel.message
    val lowPriorityTasks by sharedViewModel.lowPriorityTask.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTask.collectAsState()

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
        sharedViewModel.readSortState()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Handle database actions
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            coroutineScope.launch {
                sharedViewModel.handleDatabaseActions(action)
            }
        }
    }

    // Show snackbar for actions
    LaunchedEffect(key1 = message) {
        if (message.isNotEmpty()) {
            coroutineScope.launch {
                val label = setActionLabel(message)
                val snackBarResult = snackbarHostState.showSnackbar(
                    "$message : ${sharedViewModel.title.value}",
                    actionLabel = label,
                    duration = SnackbarDuration.Short
                )
                if (snackBarResult == SnackbarResult.ActionPerformed && label == "UNDO") {
                    sharedViewModel.action.value = Action.UNDO
                } else {
                    sharedViewModel.action.value = Action.NO_ACTION
                }
            }
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            ListAppBar(sharedViewModel, searchAppBarState, searchTextState)
        },
        content = { paddingValues ->
            ListContent(
                modifier = Modifier.padding(paddingValues),
                tasks = allTasks,
                navigateToTaskScreen,
                searchTasks,
                searchAppBarState,
                lowPriorityTasks,
                highPriorityTasks,
                sortState,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.updateTaskFields(task)
                    sharedViewModel.action.value = action
                }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        }, containerColor = Teal200, modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

private fun setActionLabel(message: String): String {
    return if (message =="Delete") "UNDO" else "OK"
}






