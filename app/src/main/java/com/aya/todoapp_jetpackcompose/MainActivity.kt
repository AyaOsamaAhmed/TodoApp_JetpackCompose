package com.aya.todoapp_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aya.todoapp_jetpackcompose.intent.TodoIntent
import com.aya.todoapp_jetpackcompose.model.local.Todo
import com.aya.todoapp_jetpackcompose.model.repository.TodoRepository
import com.aya.todoapp_jetpackcompose.ui.theme.TodoApp_JetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var repository: TodoRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoApp_JetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        val list by repository.getAllTodos()
                            .collectAsState(initial = emptyList())

                        val scope = rememberCoroutineScope()

                        MainScreen(list = list, onIntent = {
                            when (it) {
                                is TodoIntent.InsertTodo -> scope.launch(Dispatchers.IO) {
                                    repository.insertTodo(
                                        it.todo
                                    )
                                }

                                is TodoIntent.DeleteTodo -> scope.launch(Dispatchers.IO) {
                                    repository.deleteTodo(
                                        it.todo
                                    )
                                }

                                is TodoIntent.UpdateTodo -> scope.launch(Dispatchers.IO) {
                                    repository.updateTodo(
                                        it.todo
                                    )
                                }

                                else -> {}
                            }
                        })
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(list: List<Todo>, onIntent: (TodoIntent) -> Unit) {

    val title = remember {
        mutableStateOf("")
    }
    Scaffold(topBar = { Text(text = "Todo App") }) {
        Column(modifier = Modifier.padding(it).fillMaxWidth()) {
            if (list.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Data")

                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(list.size) { item ->
                        val isChecked = remember {
                            mutableStateOf(list[item].isDone)
                        }

                        Column(modifier = Modifier
                            .combinedClickable(enabled = true, onClick = {}, onLongClick = {
                                onIntent.invoke(TodoIntent.DeleteTodo(list[item]))
                            })

                            .fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = list[item].title)

                                Checkbox(checked = isChecked.value, onCheckedChange = {
                                    isChecked.value = it
                                    onIntent(TodoIntent.UpdateTodo(list[item].copy(isDone = it)))
                                })
                            }
                            HorizontalDivider()
                        }
                    }

                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    onIntent.invoke(
                        TodoIntent.InsertTodo(
                            Todo(
                                title.value,
                                id = 0,
                                isDone = false
                            )
                        )
                    )

                    title.value = ""
                }) {
                    Text(text = "Save Todo")
                }
            }
        }
    }
}