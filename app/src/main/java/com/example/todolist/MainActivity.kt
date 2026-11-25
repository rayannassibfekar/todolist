package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.model.Task
import com.example.todolist.ui.navigation.Screen
import com.example.todolist.ui.screens.*
import com.example.todolist.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TodolistTheme {
                TodoAppNavigation()
            }
        }
    }
}

@Composable
fun TodoAppNavigation() {
    val navController = rememberNavController()

    val tasks = remember {
        mutableStateListOf<Task>().apply {
            addAll(Task.getInitialSampleTasks())
        }
    }

    var nextId by remember { mutableIntStateOf(5) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Destination 1: Home / Task List (Fixed Start Destination)
        composable(Screen.Home.route) {
            HomeScreen(
                tasks = tasks,
                onToggleComplete = { id, completed ->
                    val index = tasks.indexOfFirst { it.id == id }
                    if (index != -1) {
                        tasks[index] = tasks[index].copy(completed = completed)
                    }
                },
                onDeleteTask = { id ->
                    tasks.removeAll { it.id == id }
                },
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.TaskDetail.createRoute(id))
                },
                onNavigateToAddEdit = { id ->
                    navController.navigate(Screen.AddEditTask.createRoute(id))
                },
                onNavigateToStats = {
                    navController.navigate(Screen.Stats.route)
                },
                onNavigateToAbout = {
                    navController.navigate(Screen.About.route)
                }
            )
        }

        // Destination 2: Task Details (Passing taskId argument)
        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            TaskDetailScreen(
                taskId = taskId,
                tasks = tasks,
                onToggleComplete = { id, completed ->
                    val index = tasks.indexOfFirst { it.id == id }
                    if (index != -1) {
                        tasks[index] = tasks[index].copy(completed = completed)
                    }
                },
                onDeleteTask = { id ->
                    tasks.removeAll { it.id == id }
                },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.AddEditTask.createRoute(id))
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // Destination 3: Create / Edit Task Form (Optional taskId argument)
        composable(
            route = Screen.AddEditTask.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val taskIdArg = backStackEntry.arguments?.getInt("taskId") ?: -1
            val taskId = if (taskIdArg != -1) taskIdArg else null

            AddEditTaskScreen(
                taskId = taskId,
                tasks = tasks,
                onSaveTask = { title, description, category, priority ->
                    if (taskId == null) {
                        // Creating a new task
                        tasks.add(
                            Task(
                                id = nextId++,
                                title = title,
                                description = description,
                                category = category,
                                priority = priority,
                                completed = false
                            )
                        )
                    } else {
                        // Editing existing task
                        val index = tasks.indexOfFirst { it.id == taskId }
                        if (index != -1) {
                            tasks[index] = tasks[index].copy(
                                title = title,
                                description = description,
                                category = category,
                                priority = priority
                            )
                        }
                    }
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // Destination 4: Progress & Analytics
        composable(Screen.Stats.route) {
            StatsScreen(
                tasks = tasks,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // Destination 5: About & TP Reference
        composable(Screen.About.route) {
            AboutScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
