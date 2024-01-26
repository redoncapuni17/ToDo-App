package com.example.todolist.domain.presentation.common

import android.content.Context
import android.widget.Toast

fun toastMsg(
    context: Context,    //   Mesajın gösterileceği bağlam (context).
    msg:String
){
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT   //  : Mesajın görüntüleneceği süre
    ).show()
}