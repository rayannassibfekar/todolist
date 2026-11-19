
package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val completed: Boolean = false
)

private val Purple = Color(0xFF6C63FF)
private val Background = Color(0xFFF6F7FB)
private val Dark = Color(0xFF20243A)
private val Grey = Color(0xFF85899A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Purple,
                    background = Background
                )
            ) {
                TodoHomeScreen()
            }
        }
    }
}

@Composable
fun TodoHomeScreen() {

    val tasks = remember {
        mutableStateListOf(
            Task(1, "Study Kotlin", "Learn Compose basics", "Studies", "High"),
            Task(2, "Finish my project", "Work on my TP", "Work", "Medium"),
            Task(3, "Read a book", "Read 20 pages", "Personal", "Low")
        )
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    var nextId by remember { mutableIntStateOf(4) }

    // Form state
    var showDialog by remember { mutableStateOf(false) }
    var editingTaskId by remember { mutableStateOf<Int?>(null) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Personal") }
    var priority by remember { mutableStateOf("Medium") }

    val categories = listOf("All", "Studies", "Work", "Personal")

    val completedCount = tasks.count { it.completed }

    val visibleTasks = tasks.filter {
        selectedCategory == "All" ||
                it.category == selectedCategory
    }

    Scaffold(
        containerColor = Background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingTaskId = null
                    title = ""
                    description = ""
                    category = "Personal"
                    priority = "Medium"
                    showDialog = true
                },
                containerColor = Purple,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 28.sp)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 22.dp)
        ) {

            Spacer(Modifier.height(25.dp))

            Text("Hello there 👋", fontSize = 16.sp, color = Grey)

            Text(
                "My Tasks",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Dark
            )

            Spacer(Modifier.height(22.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Purple
                )
            ) {
                Column(Modifier.padding(22.dp)) {
                    Text("Your daily progress", color = Color.White)

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "$completedCount / ${tasks.size} tasks completed",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(14.dp))

                    val progress = if (tasks.isEmpty()) 0f
                    else completedCount.toFloat() / tasks.size

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            Text(
                "Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Dark
            )

            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach { item ->
                    FilterChip(
                        selected = selectedCategory == item,
                        onClick = { selectedCategory = item },
                        label = { Text(item) }
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            Text(
                "Today's tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Dark
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(visibleTasks, key = { it.id }) { task ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked = task.completed,
                                onCheckedChange = { checked ->
                                    val index = tasks.indexOfFirst {
                                        it.id == task.id
                                    }
                                    if (index != -1) {
                                        tasks[index] =
                                            tasks[index].copy(completed = checked)
                                    }
                                }
                            )

                            Column(Modifier.weight(1f)) {
                                Text(
                                    task.title,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Dark,
                                    fontSize = 16.sp
                                )

                                Text(
                                    task.category + " • " + task.priority,
                                    color = Grey,
                                    fontSize = 12.sp
                                )

                                if (task.description.isNotBlank()) {
                                    Text(
                                        task.description,
                                        color = Grey,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                TextButton(
                                    onClick = {
                                        editingTaskId = task.id
                                        title = task.title
                                        description = task.description
                                        category = task.category
                                        priority = task.priority
                                        showDialog = true
                                    }
                                ) {
                                    Text("Edit")
                                }

                                TextButton(
                                    onClick = {
                                        tasks.removeAll { it.id == task.id }
                                    }
                                ) {
                                    Text("Delete", color = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add and Edit dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    if (editingTaskId == null) "Add a task"
                    else "Edit task"
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Task title") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") }
                    )

                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category (Studies, Work...)") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = priority,
                        onValueChange = { priority = it },
                        label = { Text("Priority (Low, Medium, High)") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = title.isNotBlank(),
                    onClick = {
                        val id = editingTaskId

                        if (id == null) {
                            tasks.add(
                                Task(
                                    id = nextId++,
                                    title = title.trim(),
                                    description = description.trim(),
                                    category = category.trim().ifBlank { "Personal" },
                                    priority = priority.trim().ifBlank { "Medium" }
                                )
                            )
                        } else {
                            val index = tasks.indexOfFirst { it.id == id }

                            if (index != -1) {
                                tasks[index] = tasks[index].copy(
                                    title = title.trim(),
                                    description = description.trim(),
                                    category = category.trim().ifBlank { "Personal" },
                                    priority = priority.trim().ifBlank { "Medium" }
                                )
                            }
                        }

                        showDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
