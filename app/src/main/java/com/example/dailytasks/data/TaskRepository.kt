package com.example.dailytasks.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TaskRepository(private val taskDao: TaskDao) {

 fun getAllTasks(): LiveData<List<Task>> {
       return taskDao.getAllTasks()
    }

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteOneTask(task: Task) {
        taskDao.deleteOneTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }


}