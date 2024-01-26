package com.example.todolist.domain.presentation.update_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.domain.presentation.MainViewModel
import com.example.todolist.domain.presentation.common.taskTextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreeen(
    id: Int,
    mainViewModel: MainViewModel,
    onBack:()-> Unit
){
    val task: String = mainViewModel.todo.task
    val isImportant:Boolean = mainViewModel.todo.isImportant
    
    LaunchedEffect(key1 = true, block = {
        mainViewModel.getTodoById(id=id)
    })
    
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update Todo") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription =null )

                    } },
                )
        },
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .height(700.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =Arrangement.Top
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Icon(imageVector = Icons.Rounded.Edit, contentDescription = null,
                modifier = Modifier.size(100.dp))

            OutlinedTextField(
                value = task,
                onValueChange = { newValue: String ->
                    mainViewModel.updateTask(newValue = newValue)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                label = {
                    Text(
                        text = "Task",
                        fontFamily = FontFamily.Monospace
                    )
                },
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp, topStart = 20.dp, topEnd = 20.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                textStyle = taskTextStyle
            )

            Row(

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Important",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Checkbox(checked = isImportant, onCheckedChange = {newValue:Boolean->
                    mainViewModel.updateIsImportat(newValue = newValue)
                })
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(

                onClick = {
                mainViewModel.updateTodo(mainViewModel.todo)
                onBack()
            }) {
                Text(
                    text = "Save Task",
                    fontSize = 16.sp,


                )
            }
        }

    }


}