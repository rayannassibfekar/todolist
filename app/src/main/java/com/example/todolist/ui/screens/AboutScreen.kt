package com.example.todolist.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("About & TP Architecture", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PurplePrimary)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "To-Do List Mobile App",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Développement et Sécurité des Applications Mobiles",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Master 2 RSE / ISII • Université d'Alger 1\nSupervised by Dr. Aggoune Soumia",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "TP Concepts Implemented",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            ConceptCard(
                title = "TP 1: Environment & Project Structure",
                description = "Gradle multiplatform setup, clean modular package layout, proper resource management (strings, colors, manifest).",
                badge = "TP 1"
            )

            Spacer(modifier = Modifier.height(10.dp))

            ConceptCard(
                title = "TP 2: Requirements & Layout Architecture",
                description = "Clear data modeling (Task with priorities & categories), responsive layout containers (Scaffold, Column, LazyColumn), and input validation.",
                badge = "TP 2"
            )

            Spacer(modifier = Modifier.height(10.dp))

            ConceptCard(
                title = "TP 3: Google Material Design 3 & Ergonomics",
                description = "Custom theme palette, consistent visual hierarchy, dark mode compatibility, linear progress feedback, and category/priority badges.",
                badge = "TP 3"
            )

            Spacer(modifier = Modifier.height(10.dp))

            ConceptCard(
                title = "TP 4: Navigation Architecture & Intents",
                description = "Hierarchical navigation graph with fixed start destination, Back Stack management, safe argument passing (taskId), and Implicit Intents (ACTION_SEND sharing, ACTION_VIEW external docs).",
                badge = "TP 4"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "External Actions (Intents Demo)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { openAndroidDocs(context) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text("🌐 Open Android Developer Docs (ACTION_VIEW)")
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = { shareProjectDetails(context) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("📤 Share Project Summary (ACTION_SEND)")
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ConceptCard(
    title: String,
    description: String,
    badge: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = PurpleContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = badge,
                        color = PurpleDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun openAndroidDocs(context: Context) {
    val webpage = Uri.parse("https://developer.android.com/guide/navigation")
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    context.startActivity(intent)
}

private fun shareProjectDetails(context: Context) {
    val info = """
        🚀 To-Do List Application - TP Android Project
        Module: Développement et Sécurité des Applications Mobiles
        University: Université d'Alger 1 (Master 2 RSE)
        Features:
        - Full Material Design 3 interface
        - Jetpack Navigation & Back Stack management
        - Dynamic category & priority filtering
        - Real-time progress analytics
        - Implicit Intent sharing
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "To-Do List TP Mobile App")
        putExtra(Intent.EXTRA_TEXT, info)
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}
