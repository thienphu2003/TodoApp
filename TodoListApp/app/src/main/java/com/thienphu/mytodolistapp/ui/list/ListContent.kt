package com.thienphu.mytodolistapp.ui.list

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.thienphu.mytodolistapp.utils.RequestState
import com.thienphu.mytodolistapp.utils.SearchAppBarState


@Composable
fun ListContent(
    modifier: Modifier,
    tasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchedTasks : RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState
) {

    if(searchAppBarState == SearchAppBarState.TRIGGERED){
        if(searchedTasks is RequestState.Success){
           HandleListContent(searchedTasks.data, navigateToTaskScreen,modifier)
        }
    }else {
        if(tasks is RequestState.Success){
            HandleListContent(tasks.data, navigateToTaskScreen,modifier)
        }
    }



}

@Composable
fun HandleListContent(
    tasks : List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    modifier: Modifier
){
    if(tasks.isEmpty()){
        EmptyContent()
    }else{
        DisplayTasks(modifier = modifier, tasks = tasks, navigateToTaskScreen = navigateToTaskScreen)
    }
}

@Composable
fun DisplayTasks(modifier: Modifier, tasks: List<ToDoTask>,
                  navigateToTaskScreen: (taskId: Int) -> Unit){
    LazyColumn(modifier = modifier) {
        items(count=tasks.size,itemContent = { item ->
            TaskItem(toDoTask = tasks[item], navigateToTaskScreen = navigateToTaskScreen)
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

        Column(modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth()) {
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
                    Canvas(modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)) {
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
@Preview
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(0, "Title", "Description", Priority.HIGH),
        navigateToTaskScreen = {})

}