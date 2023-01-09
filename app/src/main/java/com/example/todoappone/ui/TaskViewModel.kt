package com.example.todoappone.ui

import androidx.lifecycle.*
import com.example.todoappone.data.Task
import com.example.todoappone.data.TaskDao
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    val taskList: LiveData<List<Task>> = taskDao.getAll().asLiveData()

    // insert
    private fun insert(task: Task) {
        viewModelScope.launch { taskDao.insert(task) }
    }

    // update
    private fun update(task: Task) {
        viewModelScope.launch { taskDao.update(task) }
    }

    // delete
    fun delete(task: Task) {
        viewModelScope.launch { taskDao.delete(task) }
    }

    // get
    fun get(id: Int): LiveData<Task> {
        return taskDao.get(id).asLiveData()
    }

    fun addItem(title: String, isComplete: Boolean = false, rank: Int = 0): Boolean {
        val task = Task(title = title, isComplete = isComplete, rank = rank)
        if (isTaskValid(task)) {
            insert(task)
            return true
        }
        return false
    }

    private fun isTaskValid(Task: Task): Boolean {
        return Task.title.isNotBlank()
    }
}

class InventoryViewModelFactory(
    private val taskDao: TaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
