package com.thienphu.mytodolistapp.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thienphu.mytodolistapp.ToDoApplication
import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.repositories.TodoRepository
import com.thienphu.mytodolistapp.utils.SearchAppBarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch




class SharedViewModel ( val repository: TodoRepository) : ViewModel(){

    val searchAppBarState : MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState : MutableState<String> = mutableStateOf("")
    private  val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList())

    val allTasks : StateFlow<List<ToDoTask>> = _allTasks.asStateFlow()

    fun getAllTasks(){
        viewModelScope.launch {
            repository.getAllTasks.collect{
                _allTasks.value = it
            }
        }
    }
}