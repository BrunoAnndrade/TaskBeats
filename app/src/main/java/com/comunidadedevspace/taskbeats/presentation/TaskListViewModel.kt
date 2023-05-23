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


class TaskListViewModel(
    private val taskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    val taskListLiveData: LiveData<List<Task>> = taskDao.getAllLiveData()

    fun execute(taskAction: TaskAction) {

        //ação do actiontype
        when (taskAction.ActionType) {
            ActionType.DELETE.name -> deleteById(taskAction.task!!.id)
            ActionType.DELETE_ALL.name -> deleteAll()
            ActionType.CREATE.name -> insertIntoDataBase(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task!!)
        }
    }

    private fun insertIntoDataBase(task: Task) {

        viewModelScope.launch(dispatcher) {
            taskDao.insert(task)
        }
    }

    private fun updateIntoDataBase(task: Task) {
        viewModelScope.launch(dispatcher) {
            taskDao.update(task)
        }
    }

    private fun deleteAll() {
        viewModelScope.launch(dispatcher) {
            taskDao.deleteAll()
        }
    }

    private fun deleteById(id: Int) {
        viewModelScope.launch(dispatcher) {
            taskDao.deleteById(id)
        }
    }

    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBAseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBAseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}