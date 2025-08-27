package com.aya.todoapp_jetpackcompose.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE: TodoDatabase? = null

         fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()

                INSTANCE = instance
                instance
            }

        }
    }

    abstract fun getTodoDao(): TodoDao
}