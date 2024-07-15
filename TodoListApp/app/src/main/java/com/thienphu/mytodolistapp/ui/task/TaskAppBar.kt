package com.thienphu.mytodolistapp.ui.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.thienphu.mytodolistapp.components.DisplayAlertDialog
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.ui.theme.Purple500
import com.thienphu.mytodolistapp.utils.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedTask == null) {
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistedTaskAppBar(selectedTask, navigateToListScreen)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                "Add Task", color = Color.White,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
        },
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple500)
    )
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Arrow",
            tint = Color.White
        )
    }
}


@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Add Task", tint = Color.White)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistedTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                selectedTask.title, color = Color.White,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp, maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            ExistingTaskAppBarAction(selectedTask,navigateToListScreen)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple500)
    )
}

@Composable
fun ExistingTaskAppBarAction(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = "Remove ${selectedTask.title}?","Are you sure you want to remove this task",
        openDialog, onCloseDialog = { openDialog = false }, onYesClicked = {
            navigateToListScreen(Action.DELETE)
        }
    )
    DeleteAction(onDeleteClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = navigateToListScreen)
}


@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close Icon",
            tint = Color.White
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Update Icon",
            tint = Color.White
        )
    }
}


@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = Color.White
        )
    }
}


@Composable
@Preview

fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {})

}