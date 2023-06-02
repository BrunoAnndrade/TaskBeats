package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskListViewModel(taskDao:TaskDao): ViewModel() {


    val taskListLiveData: LiveData<List<Task>> = taskDao.getAllLiveData()


    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBAseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBAseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}