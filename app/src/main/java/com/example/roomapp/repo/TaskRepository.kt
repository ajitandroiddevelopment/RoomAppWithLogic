package com.example.roomapp.repo

import com.example.roomapp.data.Task
import com.example.roomapp.data.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TaskRepository @Inject
 constructor(private val taskDao: TaskDao) {

    suspend fun insert(task: Task) = taskDao.insertTask(task)

    suspend fun update(task: Task) = taskDao.updateTask(task)

    suspend fun delete(task: Task) = taskDao.deleteTask(task)

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()


}