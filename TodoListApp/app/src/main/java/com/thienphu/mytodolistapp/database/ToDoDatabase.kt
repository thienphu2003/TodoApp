package com.thienphu.mytodolistapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thienphu.mytodolistapp.dao.ToDoDao
import com.thienphu.mytodolistapp.model.ToDoTask
import com.thienphu.mytodolistapp.utils.Constants.DATABASE_NAME


@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
    companion object {
        private var db: ToDoDatabase? = null
        fun getDatabase(context: Context): ToDoDatabase {
            return db?: synchronized(this) {
                Room.databaseBuilder(context,ToDoDatabase::class.java,DATABASE_NAME)
                    .build()
                    .also { db = it }
            }
        }
    }

}