package com.example.todolist.model

data class Task(
    val id: Int,
    val title: String,
    val description: String = "",
    val category: String = "Personal",
    val priority: String = "Medium",
    val completed: Boolean = false
) {
    companion object {
        val CATEGORIES = listOf("Studies", "Work", "Personal", "Other")
        val PRIORITIES = listOf("Low", "Medium", "High")

        fun getInitialSampleTasks(): List<Task> = listOf(
            Task(
                id = 1,
                title = "Study Android Navigation",
                description = "Master Jetpack Navigation, Back Stack, and Intent mechanisms from TP4.",
                category = "Studies",
                priority = "High",
                completed = false
            ),
            Task(
                id = 2,
                title = "Finish Mobile Project",
                description = "Integrate TP concepts into our Android project for Dr. Aggoune.",
                category = "Work",
                priority = "High",
                completed = true
            ),
            Task(
                id = 3,
                title = "Read a Chapter",
                description = "Read 20 pages of Advanced Project Management.",
                category = "Personal",
                priority = "Low",
                completed = false
            ),
            Task(
                id = 4,
                title = "Review Security Concepts",
                description = "Prepare summary for Mobile Network & Security course.",
                category = "Studies",
                priority = "Medium",
                completed = false
            )
        )
    }
}
