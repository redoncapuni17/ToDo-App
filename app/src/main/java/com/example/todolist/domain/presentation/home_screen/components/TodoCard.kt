package com.example.todolist.domain.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.domain.model.Todo
import com.example.todolist.domain.presentation.common.taskTextStyle

@Composable
fun TodoCard(
    todo: Todo,
    onDone:()-> Unit,
    onUpdate:(id:Int)->Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .shadow(3.dp),
        shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp, topStart = 5.dp, topEnd = 5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { onDone() },
                modifier = Modifier.weight(1f)
            ) { 
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
            }
            Text(text = todo.task,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(8f),
                style = taskTextStyle
                )
            if(todo.isImportant){
                Icon(imageVector = Icons.Rounded.Star, contentDescription =null,
                    modifier = Modifier.weight(1f)
                    )
            }
            IconButton(onClick = { onUpdate(todo.id) },
                modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null )
            }
        }
    }
}