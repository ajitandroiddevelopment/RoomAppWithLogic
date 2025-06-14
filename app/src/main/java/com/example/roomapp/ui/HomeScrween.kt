package com.example.roomapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomapp.data.Task
import com.example.roomapp.vm.TaskViewModel

@Composable
fun HomeScreen(
    viewModel: TaskViewModel = hiltViewModel()

) {

    // Use collectAsState to convert the Flow to a State
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())

    // State variables for input fields
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    // State variables for input fields
    var titleField by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionField by remember { mutableStateOf(TextFieldValue("")) }


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAF4F6))
        ,
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {




        Card(
            modifier = Modifier.padding(top=30.dp , bottom = 20.dp , start = 20.dp , end = 20.dp),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF7F7FC),
                                Color(0xFFA4D8EF)

                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = titleField,
                        onValueChange = { titleField = it },
                        label = { Text("Title") },
                        modifier = Modifier.padding(16.dp)
                    )

                    OutlinedTextField(
                        value = descriptionField,
                        onValueChange = { descriptionField = it },
                        label = { Text("Description") },
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(onClick = {
                        // If selectedTask is null, add a new task; otherwise, update the existing task
                        if (selectedTask == null) {
                            viewModel.addTask(Task(title = titleField.text, description = descriptionField.text))
                        } else {
                            // Update the selected task with the new title and description
                            viewModel.updateTask(
                                selectedTask!!.copy(
                                    title = titleField.text,
                                    description = descriptionField.text
                                )
                            )
                            // Reset the selected task after updating
                            selectedTask = null
                        }
                        // Clear the input fields after adding/updating the task
                        titleField = TextFieldValue("")
                        descriptionField = TextFieldValue("")
                    }) {
                        // Display "Add Task" or "Update Task" based on the selected task
                        Text(if (selectedTask == null) "Add Task" else "Update Task")
                    }

                }
            }
        }


       HorizontalDivider()
        LazyColumn {


            items(tasks.value) { task ->
                TaskItem(task = task, onEdit = { selected ->
                    // here selected task is set to the selected task
                    selectedTask = selected
                    // title and description are set to the title and description of the selected task
                    titleField = TextFieldValue(selected.title, TextRange(selected.title.length))
                    descriptionField = TextFieldValue(selected.description, TextRange(selected.description.length))
                })
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun previewHomeScreen() {
    HomeScreen()

}

@Composable
fun TaskItem(
    task: Task,
    viewModel: TaskViewModel = hiltViewModel(),
    onEdit: (Task) -> Unit
) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFFF7F7FC), Color(0xFFA4D8EF))
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(task.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(task.description, fontSize = 15.sp)
                }
                Row {
                    IconButton(onClick = { onEdit(task) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { viewModel.deleteTask(task) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}
