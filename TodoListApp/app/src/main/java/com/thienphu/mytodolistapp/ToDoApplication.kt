package com.thienphu.mytodolistapp

import android.app.Application
import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.database.ToDoDatabase
import com.thienphu.mytodolistapp.repositories.TodoRepository




class ToDoApplication : Application() {

    lateinit var repository: TodoRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = ToDoDatabase.getDatabase(this)
        val dao = database.todoDao()
        repository = TodoRepository(dao)
    }
}
