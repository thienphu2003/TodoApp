package com.thienphu.mytodolistapp.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thienphu.mytodolistapp.components.PriorityDropDown
import com.thienphu.mytodolistapp.model.Priority


@Composable
fun TaskContent(
    modifier: Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background).padding(12.dp)

    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Title")
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true
        )
        Divider(modifier = Modifier.height(8.dp), color = MaterialTheme.colorScheme.background)
        PriorityDropDown(priority, onPrioritySelected = onPrioritySelected)
        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier.fillMaxSize(),
            label = {
                Text(text = "Description")
            },
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}


@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        modifier = Modifier,
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {})

}