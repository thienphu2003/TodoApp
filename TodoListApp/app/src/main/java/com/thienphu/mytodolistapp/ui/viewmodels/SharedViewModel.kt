package com.thienphu.mytodolistapp.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thienphu.mytodolistapp.ToDoApplication
import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.model.Priority
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.repositories.TodoRepository
import com.thienphu.mytodolistapp.utils.RequestState
import com.thienphu.mytodolistapp.utils.SearchAppBarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch




class SharedViewModel ( val repository: TodoRepository) : ViewModel(){

    val id : MutableState<Int> = mutableStateOf(0)
    val title : MutableState<String> = mutableStateOf("")
    val description : MutableState<String> = mutableStateOf("")
    val priority : MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState : MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState : MutableState<String> = mutableStateOf("")
    private  val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)

    val allTasks : StateFlow<RequestState<List<ToDoTask>>> = _allTasks.asStateFlow()

    fun getAllTasks(){
        _allTasks.value = RequestState.Loading
       try {
           viewModelScope.launch {
               repository.getAllTasks.collect{
                   _allTasks.value = RequestState.Success(it)
               }
           }
       }catch (e: Exception){
           _allTasks.value = RequestState.Error(e)
       }
    }


    private val _selected_task  : MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selected_task : StateFlow<ToDoTask?> = _selected_task.asStateFlow()

    fun getSelectedTask(taskId: Int){
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect {
                _selected_task.value = it
            }
        }
    }


    fun updateTaskFields(toDoTask: ToDoTask?){
        if(toDoTask !==null){
            id.value = toDoTask.id
            title.value = toDoTask.title
            description.value = toDoTask.description
            priority.value = toDoTask.priority
        }else {
            id.value = 0
            title.value = ""
            description.value =""
            priority.value = Priority.LOW
        }

    }

    fun updateTitle (newTitle : String){
        if(newTitle.length < 20){
            title.value = newTitle
        }
    }



}