package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.model.Note
import com.example.noteapp.ui.theme.NoteAppTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny // Biểu tượng cho chế độ sáng
import androidx.compose.material.icons.filled.Nightlight // Biểu tượng cho chế độ tối
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) } // Trạng thái chế độ tối

            NoteAppTheme(darkTheme = isDarkMode) { // Truyền chế độ vào NoteAppTheme
                MainScreen(isDarkMode) { isDarkMode = !isDarkMode } // Truyền hàm để chuyển đổi chế độ
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(isDarkMode: Boolean, toggleDarkMode: () -> Unit) {
    var notes by remember { mutableStateOf(listOf<Note>()) }
    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ghi chú đơn giản", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    // Nút chuyển đổi chế độ tối
                    IconButton(onClick = { toggleDarkMode() }) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.WbSunny else Icons.Default.Nightlight,
                            contentDescription = "Toggle Dark Mode"
                        )
                    }
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* TODO: Implement settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (noteTitle.isNotBlank() && noteContent.isNotBlank()) {
                        notes = notes + Note(
                            id = notes.size + 1,
                            title = noteTitle,
                            content = noteContent
                        )
                        noteTitle = ""
                        noteContent = ""
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                content = { Icon(Icons.Default.Add, contentDescription = "Add Note") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.medium // Hình dạng góc tròn
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Nội dung") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.medium // Hình dạng góc tròn
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notes) { note ->
                    NoteCard(note = note)
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Note) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp), // Tăng độ cao cho hiệu ứng nổi
        shape = MaterialTheme.shapes.medium // Sử dụng hình dạng góc tròn
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {
        MainScreen(false) {}
    }
}
