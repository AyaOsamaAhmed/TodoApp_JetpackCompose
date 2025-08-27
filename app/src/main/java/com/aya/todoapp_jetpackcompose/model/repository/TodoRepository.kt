package com.aya.todoapp_jetpackcompose.model.repository

import com.aya.todoapp_jetpackcompose.model.local.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getAllTodos(): Flow<List<Todo>>

}