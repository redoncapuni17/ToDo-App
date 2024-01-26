package com.example.todolist.domain.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.domain.model.Todo
import com.example.todolist.domain.presentation.MainViewModel
import com.example.todolist.domain.presentation.common.taskTextStyle
import com.example.todolist.domain.presentation.common.toastMsg
import kotlinx.coroutines.job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog_HomeSc(
    openDialod: Boolean,
    onClose:()->Unit,
    mainViewModel:MainViewModel
){
    var text:String by remember { mutableStateOf("") } //  MutableState, uygulama içindeki değişken durumu temsil eder ve bu durumu güncelleyebilirsiniz.
    var isImportant by remember { mutableStateOf(false) }

    val todo = Todo(0,text,isImportant)

    val focusRequester= FocusRequester()
    val context = LocalContext.current

    if(openDialod){
        AlertDialog(

            title = {
                    Text(
                        text ="Todo",
                        fontFamily = FontFamily.Serif
                    )
            },
            text = {
                   LaunchedEffect(                  // Pencere açıldığında otomatik olarak metin girişine odaklanmak için
                       key1 = true,
                       block = {
                           coroutineContext.job.invokeOnCompletion {
                               focusRequester.requestFocus()
                           }
                       })

                Column(
                    modifier = Modifier.height(120.dp)
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            text=it
                        },
                        label =  {
                            Text(
                                text = "Add Task",
                                fontFamily = FontFamily.Monospace
                                )
                        },

                        shape = RoundedCornerShape(10.dp),

                        modifier = Modifier.focusRequester(focusRequester).fillMaxWidth().padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if(text.isNotBlank()){
                                    mainViewModel.insertTodo(todo=todo)
                                    text = ""
                                    isImportant = false
                                    onClose()
                                }else{
                                    toastMsg(
                                        context,
                                        "Empty Task!"
                                    )
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { text= ""}) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null)
                            }
                        },
                        textStyle = taskTextStyle
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Important",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Checkbox(
                            checked =isImportant ,
                            onCheckedChange ={isImportant = it} )
                    }

                }
            },
            onDismissRequest = {
                onClose()
                text=""
                isImportant = false
            },
            confirmButton = {
                Button(onClick = {
                    if(text.isNotBlank()){
                    mainViewModel.insertTodo(todo=todo)
                    text = ""
                    isImportant = false
                    onClose()
                }else{
                    toastMsg(
                        context,
                        "Empty Task!"
                    )
                }  }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onClose()
                    text = ""
                    isImportant = false }) {
                    Text(text = "Close")
                }
            }
        )
    }
}