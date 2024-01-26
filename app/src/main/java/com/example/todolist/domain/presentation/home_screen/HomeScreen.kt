package com.example.todolist.domain.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.domain.presentation.MainViewModel
import com.example.todolist.domain.presentation.common.mySnackbar
import com.example.todolist.domain.presentation.home_screen.components.AlertDialog_HomeSc
import com.example.todolist.domain.presentation.home_screen.components.EmptyTaskScreen
import com.example.todolist.domain.presentation.home_screen.components.TodoCard
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onUpdate:(id:Int)-> Unit
){
    val todo by mainViewModel.getAllTodos.collectAsStateWithLifecycle(initialValue = emptyList())

    var  openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val scope:CoroutineScope = rememberCoroutineScope()
    val snackbarHostState:SnackbarHostState = remember {SnackbarHostState()}  // SnackbarHostState kullanılarak geri alınabilir ("undo") işlemi için Snackbar bildirimleri yönetilir.

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(

        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

        topBar = {
                    Column(modifier = Modifier
                        .padding(bottom = 5.dp)
                        .background(
                            color = Color.hsl(hue = 240f, saturation = 1f, lightness = 0.75f),
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))

                    ) {
                        Text(text = "ToDo App", fontSize = 18.sp,modifier = Modifier.padding(start = 16.dp, top = 16.dp))
                        Text(text = "Welcome Back", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp))

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 7.dp, bottom = 16.dp, end = 16.dp),

                            shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp, topStart = 30.dp, topEnd = 30.dp),
                            label = { Text("Search") },
                        )
                    }


        },

        floatingActionButton = {

            FloatingActionButton(onClick = { openDialog = true }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },


    ) { paddingValues ->
        AlertDialog_HomeSc(
            openDialod = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel
        )
        if (todo.isEmpty()) {
            EmptyTaskScreen(paddingValues = paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = todo.filter {
                        it.task.contains(searchQuery, ignoreCase = true)
                    },
                    key = { it.id }
                ) { todo ->
                    TodoCard(

                        todo = todo,
                        onDone = {
                            mainViewModel.deleteTodo(todo = todo)
                            mySnackbar(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                                msg = "DONE! -> \"${todo.task}\"",
                                actionLabel = "UNDO",
                                onAction = { mainViewModel.undoDeletedTodo() }
                            )
                        },
                        onUpdate = onUpdate
                    )
                }
            }
        }
    }

}