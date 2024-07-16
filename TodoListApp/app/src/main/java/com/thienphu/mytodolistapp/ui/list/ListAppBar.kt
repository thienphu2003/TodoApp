package com.thienphu.mytodolistapp.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha

import com.thienphu.mytodolistapp.R
import com.thienphu.mytodolistapp.components.DisplayAlertDialog
import com.thienphu.mytodolistapp.components.PriorityItem
import com.thienphu.mytodolistapp.model.Priority
import com.thienphu.mytodolistapp.ui.theme.Purple500

import com.thienphu.mytodolistapp.ui.theme.topAppBarBackgroundColor
import com.thienphu.mytodolistapp.ui.theme.topAppBarContentColor
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.SearchAppBarState
import kotlin.math.sin

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
){

    when(searchAppBarState){
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {
                    sharedViewModel.saveSortingState(it)
                },
                onDeleteAllClicked = {
                    sharedViewModel.action.value = Action.DELETE_ALL
                }
            )
        }
        else -> {
            SearchAppBar( text = searchTextState, onSearchClicked = {
                sharedViewModel.getAllSearchedTasks(it)
            }, onCloseClicked = {
                sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                sharedViewModel.searchTextState.value = ""
            }, onTextChange = {
                sharedViewModel.searchTextState.value = it
            })
        }
    }




}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
){

       TopAppBar(
           title = {
               Text("Tasks")
           },
           colors = TopAppBarDefaults.topAppBarColors(
               containerColor = topAppBarBackgroundColor,
               titleContentColor = topAppBarContentColor
           ),
           actions = {
               ListAppBarActions(onSearchClicked, onSortClicked,onDeleteAllClicked)
           }

       )

}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
){
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = "Remove All Tasks?","Are you sure you want to remove all tasks",
        openDialog, onCloseDialog = { openDialog = false }, onYesClicked = {
            onDeleteAllClicked()
        }
    )
    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAllAction(onDeleteAllClicked = {
        openDialog = true
    })
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
){
    IconButton(onClick = {onSearchClicked()}) {
        Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(R.string.search_action), tint = topAppBarContentColor)
    }
}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(R.string.sort_action),
            tint = topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(2.dp, 2.dp)
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                },
                text = {
                    PriorityItem(Priority.LOW)
                },
            )
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                },
                text = {
                    PriorityItem(Priority.HIGH)
                }
            )
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                },
                text = {
                    PriorityItem(Priority.NONE)
                },

            )
        }
    }
}

@Composable
fun DeleteAllAction(onDeleteAllClicked: () -> Unit){
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertial_menu),
            contentDescription = stringResource(R.string.delete_all_action),
            tint = topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(2.dp, 2.dp)
        ) {
            DropdownMenuItem(
                onClick = {
                    onDeleteAllClicked()
                    expanded = false},
                text = {
                    Text(stringResource(R.string.delete_all_action), modifier = Modifier.padding(start = 12.dp),color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

      Column(modifier = Modifier.fillMaxWidth().height(100.dp).background(Purple500)) {
          Spacer(modifier = Modifier.height(40.dp))
          TextField(
              modifier = Modifier.fillMaxWidth(),
              value = text,
              onValueChange = { onTextChange(it) },
              placeholder = {
                  Text(
                      modifier = Modifier.alpha(ContentAlpha.medium),
                      text = "Search",
                      color = Color.White,
                      fontSize = 20.sp
                  )
              },
              textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
              singleLine = true,
              leadingIcon = {
                  IconButton(onClick = {}, modifier = Modifier.alpha(ContentAlpha.disabled)) {
                      Icon(
                          imageVector = Icons.Filled.Search,
                          contentDescription = "Search Icon",
                          tint = Color.White
                      )
                  }
              },
              colors = TextFieldDefaults.textFieldColors(
                  containerColor = Purple500,
                  cursorColor = Color.White,
                  focusedIndicatorColor = Color.Transparent,
                  disabledIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent
              ),
              trailingIcon = {
                  IconButton(onClick = {
                      if(text.isNotEmpty()){
                          onTextChange("")
                      }else {
                          onCloseClicked()
                      }
                  }) {
                      Icon(
                          imageVector = Icons.Filled.Close,
                          contentDescription = "Close Icon",
                          tint = Color.White
                      )
                  }
              },
              keyboardOptions = KeyboardOptions(
                  imeAction = ImeAction.Search
              ),
              keyboardActions = KeyboardActions(
                  onSearch = {
                      onSearchClicked(text)
                  }
              )
          )
      }

}



@Composable
@Preview
fun SearchAppBarPreview(){
    SearchAppBar(text = "Search", onTextChange = {}, onCloseClicked = {}, onSearchClicked = {})
}




