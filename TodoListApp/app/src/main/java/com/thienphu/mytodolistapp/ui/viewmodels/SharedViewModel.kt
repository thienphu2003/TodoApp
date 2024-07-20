package com.thienphu.mytodolistapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thienphu.mytodolistapp.ToDoApplication
import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.model.Priority
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.repositories.DataStoreRepository
import com.thienphu.mytodolistapp.repositories.TodoRepository
import com.thienphu.mytodolistapp.utils.Action
import com.thienphu.mytodolistapp.utils.RequestState
import com.thienphu.mytodolistapp.utils.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch




class SharedViewModel ( private val repository: TodoRepository , private val dataStoreRepository: DataStoreRepository) :
    ViewModel(){

    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    val message: MutableState<String> = mutableStateOf("")



    val id : MutableState<Int> = mutableStateOf(0)
    val title : MutableState<String> = mutableStateOf("")
    val description : MutableState<String> = mutableStateOf("")
    val priority : MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState : MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState : MutableState<String> = mutableStateOf("")
    private  val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks : StateFlow<RequestState<List<ToDoTask>>> = _allTasks.asStateFlow()

    private  val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks : StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks.asStateFlow()

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

    fun getAllSearchedTasks(searchContent :String){
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchContent = "%$searchContent%").collect{
                    _searchedTasks.value = RequestState.Success(it)
                }
            }
        }catch (e: Exception){
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
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


    private fun addTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask : ToDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value

            )
            repository.addTask(todoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask : ToDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value

            )
            repository.updateTask(todoTask)
        }
    }

    private fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask : ToDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value

            )
            repository.deleteTask(todoTask)
        }
    }

    private  fun deleteAllTasks(){


        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
        }
    }

    fun handleDatabaseActions(action:Action)  {
        Log.d("Handle database actions", "Trigger")
        when(action){
            Action.ADD -> {
                addTask()
                message.value = "Add"
            }
            Action.UPDATE -> {
                updateTask()
                message.value ="Update"
            }
            Action.DELETE -> {
                deleteTask()
                message.value = "Delete"
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
                message.value = "All Tasks Removed"
            }
            Action.UNDO -> {
                addTask()
                message.value = "Undo"
            }
            else -> {
                message.value = ""
            }

        }


    }


    fun updateTaskFields(toDoTask: ToDoTask?){
        if(toDoTask !== null){
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


    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }


    val lowPriorityTask : StateFlow<List<ToDoTask>> = repository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )
    val highPriorityTask : StateFlow<List<ToDoTask>> = repository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )
    private  val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState : StateFlow<RequestState<Priority>> = _sortState.asStateFlow()

    fun readSortState(){
        Log.d("SortState", "readSortState:")
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState.map{
                     Priority.valueOf(it)
                }.collect{
                    _sortState.value = RequestState.Success(it)
                }
            }
        }catch (e: Exception){
            _sortState.value = RequestState.Error(e)
        }
    }

    fun saveSortingState(priority: Priority){
        viewModelScope.launch(Dispatchers.IO){
            dataStoreRepository.persistSortState(priority)
        }
    }



}