package com.example.todolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

// Data Access Object
@Dao
interface TodoDao {
    // Bu işlevler, sırasıyla bir todo nesnesini veritabanına eklemek, güncellemek ve silmek için kullanılır.

    @Insert(onConflict = OnConflictStrategy.REPLACE) // OnConflictStrategy.REPLACE kullanılarak mevcut öğe değiştirilir veya yeni bir öğe eklenir.
    suspend fun  insertTodo(todo: Todo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM Todo WHERE id = :id") //@Query annotation'ları: Bu işlevler, özel SQL sorgularını tanımlar. İlk sorgu, belirli bir id'ye sahip bir Todo öğesini almak için kullanılır. İkinci sorgu, tüm Todo öğelerini almak için kullanılır.
    suspend fun  getTodoById(id:Int) : Todo

    @Query("SELECT *FROM Todo")
    fun getAllTodos():Flow<List<Todo>> //getAllTodos() fonksiyonu: Bu, tüm Todo öğelerini almak için kullanılır ve Flow<List<Todo>> türünde bir akış döndürür. Flow, verilerin asenkron olarak akışını sağlayan bir Kotlin coroutines kütüphanesi özelliğidir.

}