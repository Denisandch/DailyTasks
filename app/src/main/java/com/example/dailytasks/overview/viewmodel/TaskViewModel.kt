package com.example.dailytasks.overview.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.dailytasks.data.Task
import com.example.dailytasks.data.TaskDatabase
import com.example.dailytasks.data.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val _getAllTasks: LiveData<List<Task>>
    val getAllTasks: LiveData<List<Task>>

    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        _getAllTasks = repository.getAllTasks()
        getAllTasks = _getAllTasks
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteOneTask(task: Task) {
        viewModelScope.launch {
            repository.deleteOneTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }

}