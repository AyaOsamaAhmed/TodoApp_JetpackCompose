package com.aya.todoapp_jetpackcompose.model.di

import android.content.Context
import com.aya.todoapp_jetpackcompose.model.local.TodoDao
import com.aya.todoapp_jetpackcompose.model.local.TodoDatabase
import com.aya.todoapp_jetpackcompose.model.repository.TodoRepoImpl
import com.aya.todoapp_jetpackcompose.model.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ModelModule {


    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return TodoDatabase.getInstance(context)
    }

    @Provides
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.getTodoDao()
    }

    @Provides
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepoImpl(todoDao)
    }


}