package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.local.TodoDao
import com.example.todolist.data.local.TodoDatabase
import com.example.todolist.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//  Dagger Hilt, bağımlılık enjeksiyonu (dependency injection) yapısını sağlayan bir kütüphanedir

@Module        //  @Module açıklaması, bu sınıfın bir Dagger modülü olduğunu belirtir.
@InstallIn(SingletonComponent::class)    // @InstallIn açıklaması ise bu modülün hangi bileşende kurulacağını belirtir.
object AppModule {

    @Provides     //  bir bağımlılığı sağlayan bir metodu işaretler.
    @Singleton    // @Singleton notasyonu, ilgili bağımlılığın uygulama yaşam döngüsü boyunca yalnızca bir kez oluşturulacağını belirtir.
    fun providesLocalDatabase(@ApplicationContext context: Context):TodoDatabase{
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "local_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTodoDao(db:TodoDatabase):TodoDao = db.todoDao()

    @Provides
    @Singleton
    fun providesTodoRepository(dao:TodoDao):TodoRepository = TodoRepository(dao = dao)
}