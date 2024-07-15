package com.thienphu.mytodolistapp.ui.list

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thienphu.mytodolistapp.model.Priority
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.ui.theme.highPriorityColor
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.RequestState
import com.thienphu.mytodolistapp.utils.SearchAppBarState


@Composable
fun ListContent(
    modifier: Modifier,
    tasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    lowPriorityTask: List<ToDoTask>,
    highPriorityTask: List<ToDoTask>,
    sortState: RequestState<Priority>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(searchedTasks.data, navigateToTaskScreen,onSwipeToDelete,modifier)
                }
            }

            sortState.data == Priority.NONE -> {
                if (tasks is RequestState.Success) {
                    HandleListContent(tasks.data, navigateToTaskScreen,onSwipeToDelete, modifier)
                }
            }

            sortState.data == Priority.HIGH -> {

                HandleListContent(highPriorityTask, navigateToTaskScreen,onSwipeToDelete, modifier)

            }

            sortState.data == Priority.LOW -> {

                HandleListContent(lowPriorityTask, navigateToTaskScreen, onSwipeToDelete,modifier)

            }
        }


    }


}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    modifier: Modifier
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    modifier: Modifier, tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(count = tasks.size, itemContent = { item ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if(isDismissed && dismissDirection == DismissDirection.EndToStart){

            }
            val degree by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f,
                label = "Dismiss"
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = { RedBackGround(degrees = degree) },
                dismissContent = {
                    TaskItem(toDoTask = tasks[item], navigateToTaskScreen = navigateToTaskScreen)
                }
            )
        })
    }
}


@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RectangleShape)
            .clickable {
                navigateToTaskScreen(toDoTask.id)
            }) {

        Column(
            modifier = Modifier
                .padding(all = 12.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = Color.DarkGray,
                    style = TextStyle(fontSize = 24.sp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(16.dp)
                            .height(16.dp)
                    ) {
                        drawCircle(toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = Color.DarkGray,
                style = TextStyle(fontSize = 20.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}

@Composable
fun RedBackGround(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(highPriorityColor)
            .padding(horizontal = 20.dp)
    ) {
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = Color.White
        )
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(0, "Title", "Description", Priority.HIGH),
        navigateToTaskScreen = {})

}