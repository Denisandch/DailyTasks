package com.example.dailytasks.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("select * from all_tasks order by id asc")
    fun getAllTasks(): LiveData<List<Task>>

    @Update()
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteOneTask(task: Task)

    @Query("delete from all_tasks")
    suspend fun deleteAllTasks()

}