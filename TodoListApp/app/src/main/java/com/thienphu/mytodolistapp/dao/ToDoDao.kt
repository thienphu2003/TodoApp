package com.thienphu.mytodolistapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thienphu.mytodolistapp.model.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM my_todo_table ORDER BY id ASC")
    fun getAllTasks() : Flow<List<ToDoTask>>

    @Query("SELECT * FROM my_todo_table WHERE id = :taskId")
    fun getSelectedTask(taskId : Int) : Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task : ToDoTask)

    @Update
    suspend fun updateTask(task : ToDoTask)


    @Delete
    suspend fun  deleteTask(task : ToDoTask)

    @Query("DELETE FROM my_todo_table")
    suspend fun deleteAllTasks()


    @Query("SELECT * FROM my_todo_table WHERE title LIKE :searchContent OR description LIKE :searchContent")
    fun search(searchContent : String ): Flow<List<ToDoTask>>

    @Query("SELECT * FROM my_todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortFromLowToHigh() : Flow<List<ToDoTask>>

    @Query("SELECT * FROM my_todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortFromHighToLow() : Flow<List<ToDoTask>>


}