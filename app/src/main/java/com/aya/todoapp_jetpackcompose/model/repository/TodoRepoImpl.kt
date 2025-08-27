package com.aya.todoapp_jetpackcompose.model.repository

import com.aya.todoapp_jetpackcompose.model.local.Todo
import com.aya.todoapp_jetpackcompose.model.local.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepoImpl (val todoDao: TodoDao) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos()
    }
}