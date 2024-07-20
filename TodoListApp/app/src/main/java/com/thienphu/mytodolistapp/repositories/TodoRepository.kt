package com.thienphu.mytodolistapp.repositories

import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.model.ToDoTask
import kotlinx.coroutines.flow.Flow


class TodoRepository(private val todoDao: ToDoDao) {

    val getAllTasks : Flow<List<ToDoTask>> = todoDao.getAllTasks()

    val sortByLowPriority : Flow<List<ToDoTask>> = todoDao.sortFromLowToHigh()

    val sortByHighPriority : Flow<List<ToDoTask>> = todoDao.sortFromHighToLow()

    fun getSelectedTask(taskId: Int) : Flow<ToDoTask> = todoDao.getSelectedTask(taskId)

    suspend fun  addTask (todoTask: ToDoTask)  = todoDao.addTask(todoTask)

    suspend fun updateTask(todoTask: ToDoTask) = todoDao.updateTask(todoTask)

    suspend fun  deleteTask (todoTask: ToDoTask) = todoDao.deleteTask(todoTask)

    suspend fun  deleteAllTask() = todoDao.deleteAllTasks()

    fun searchDatabase(searchContent : String) : Flow<List<ToDoTask>> = todoDao.search(searchContent)

}