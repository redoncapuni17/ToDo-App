package com.example.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.domain.model.Todo


@Database(entities = [Todo::class], version = 1, exportSchema = true)  //  entities: Bu parametre, veritabanında bulunacak varlıkları belirtir. Burada, sadece Todo sınıfı belirtilmiştir.
abstract class TodoDatabase:RoomDatabase() {

    abstract fun todoDao(): TodoDao  // Bu abstract fonksiyon, TodoDao arabirimini döndürerek, veritabanına erişim sağlayan bir DAO (Data Access Object) nesnesini elde etmek için kullanılır.
}