package com.example.todolist.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int): String = "task_detail/$taskId"
    }
    object AddEditTask : Screen("add_edit?taskId={taskId}") {
        fun createRoute(taskId: Int? = null): String =
            if (taskId != null) "add_edit?taskId=$taskId" else "add_edit"
    }
    object Stats : Screen("stats")
    object About : Screen("about")
}
