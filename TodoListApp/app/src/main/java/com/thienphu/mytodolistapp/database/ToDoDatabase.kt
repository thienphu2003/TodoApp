package com.thienphu.mytodolistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.model.ToDoTask


@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao

}