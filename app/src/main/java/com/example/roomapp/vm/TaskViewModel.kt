package com.example.roomapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.Task
import com.example.roomapp.repo.TaskRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel  @Inject  constructor(private val taskRepository: TaskRepository) : ViewModel() {

    val tasks = taskRepository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun addTask(task : Task){
        viewModelScope.launch {
            taskRepository.insert(task)
        }
    }
    fun updateTask(task : Task){
        viewModelScope.launch {
            taskRepository.update(task)
        }
    }
    fun deleteTask(task : Task){
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }
//    fun getAllTasks(){
//        viewModelScope.launch {
//            taskRepository.getAllTasks()
//        }
//    }
}