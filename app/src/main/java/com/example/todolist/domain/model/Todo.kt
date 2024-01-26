package com.example.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity  //  Bu annotation, bu sınıfın bir Room veritabanı tablosu olduğunu belirtir
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val task: String,
    val isImportant: Boolean

)
