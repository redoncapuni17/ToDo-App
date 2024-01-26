package com.example.todolist.domain.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoRepository
):ViewModel() {
    var todo by  mutableStateOf(Todo(0,"",false))    // güncellenebilen bir durumu temsil eder
        private set

    val getAllTodos : kotlinx.coroutines.flow.Flow<List<Todo>> = repository.getAllTodos()

    private var deleteTodo: Todo? = null

    fun insertTodo(todo: Todo){
        viewModelScope.launch( Dispatchers.IO ) {
            repository.insertTodo(todo=todo)
        }
    }
    fun updateTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo = todo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            deleteTodo = todo
            repository.deleteTodo(todo = todo)
        }
    }

    fun undoDeletedTodo(){
        deleteTodo?.let { todo ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(todo = todo)
            }
        }
    }

    fun getTodoById(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            todo =repository.getTodoById(id= id)

        }
    }

    fun updateTask(newValue: String){
        todo = todo.copy(task = newValue)
    }

    fun updateIsImportat(newValue: Boolean){
        todo = todo.copy(isImportant = newValue)
    }
}