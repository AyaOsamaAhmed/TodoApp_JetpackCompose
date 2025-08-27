package com.aya.todoapp_jetpackcompose.intent

import com.aya.todoapp_jetpackcompose.model.local.Todo

sealed class TodoIntent {

    data class InsertTodo(val todo: Todo) : TodoIntent()

    data class UpdateTodo(val todo: Todo) : TodoIntent()

    data class DeleteTodo(val todo: Todo) : TodoIntent()

    object GetAllTodos : TodoIntent()

}