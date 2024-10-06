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
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.style.TextOverflow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            NoteAppTheme(darkTheme = isDarkMode) {
                MainScreen(isDarkMode) { isDarkMode = !isDarkMode }
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
    var noteToEdit by remember { mutableStateOf<Note?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }
    var noteToView by remember { mutableStateOf<Note?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }

    // Lọc ghi chú dựa trên từ khóa tìm kiếm
    val filteredNotes = notes.filter { note ->
        note.title.contains(searchQuery, ignoreCase = true) ||
                note.content.contains(searchQuery, ignoreCase = true)
    }

    // Tab Layout
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Danh sách ghi chú", "Thêm ghi chú")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ghi chú", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { toggleDarkMode() }) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.WbSunny else Icons.Default.Nightlight,
                            contentDescription = "Toggle Dark Mode"
                        )
                    }
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedTabIndex == 0) {
                FloatingActionButton(
                    onClick = { selectedTabIndex = 1 }, // Chuyển đến tab thêm ghi chú
                    containerColor = MaterialTheme.colorScheme.primary,
                    content = { Icon(Icons.Default.Add, contentDescription = "Add Note") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTabIndex) {
                0 -> {
                    // Tab danh sách ghi chú
                    if (isSearchVisible) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Tìm kiếm ghi chú...") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    LazyColumn {
                        items(filteredNotes) { note ->
                            NoteCard(
                                note = note,
                                onView = { noteToView = it },
                                onEdit = {
                                    noteToEdit = it
                                    noteTitle = it.title
                                    noteContent = it.content
                                    selectedTabIndex = 1 // Chuyển đến tab thêm ghi chú
                                    noteToView = null // Đóng hộp thoại
                                },
                                onDelete = {
                                    noteToDelete = it
                                    showDeleteConfirmation = true
                                }
                            )
                        }
                    }
                }
                1 -> {
                    // Tab thêm ghi chú
                    TextField(
                        value = noteTitle,
                        onValueChange = { noteTitle = it },
                        label = { Text("Tiêu đề") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        ),
                        shape = MaterialTheme.shapes.medium
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
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            // Kiểm tra xem có đang chỉnh sửa ghi chú hay không
                            if (noteToEdit != null) {
                                // Cập nhật ghi chú hiện có
                                notes = notes.map { note ->
                                    if (note.id == noteToEdit!!.id) {
                                        note.copy(title = noteTitle, content = noteContent)
                                    } else {
                                        note
                                    }
                                }
                                noteToEdit = null // Đặt lại noteToEdit về null
                            } else if (noteTitle.isNotBlank() && noteContent.isNotBlank()) {
                                // Thêm ghi chú mới nếu không có ghi chú nào đang chỉnh sửa
                                notes = notes + Note(
                                    id = notes.size + 1,
                                    title = noteTitle,
                                    content = noteContent,
                                    timestamp = System.currentTimeMillis()
                                )
                            }
                            // Đặt lại tiêu đề và nội dung ghi chú sau khi lưu
                            noteTitle = ""
                            noteContent = ""
                            selectedTabIndex = 0 // Quay lại tab danh sách ghi chú
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Lưu ghi chú")
                    }

                }
            }
        }
    }

    // Hộp thoại xác nhận xóa
    if (showDeleteConfirmation && noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Xóa ghi chú") },
            text = { Text("Bạn có chắc chắn muốn xóa ghi chú này?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        notes = notes.filter { it.id != noteToDelete!!.id }
                        showDeleteConfirmation = false
                        noteToDelete = null
                    }
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Hủy")
                }
            }
        )
    }

    // Hộp thoại xem chi tiết
    if (noteToView != null) {
        AlertDialog(
            onDismissRequest = { noteToView = null },
            title = { Text(noteToView!!.title, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = { Text(noteToView!!.content) },
            confirmButton = {
                TextButton(onClick = { noteToView = null }) {
                    Text("Đóng")
                }
            }
        )
    }
}

@Composable
fun NoteCard(note: Note, onView: (Note) -> Unit, onEdit: (Note) -> Unit, onDelete: (Note) -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(note.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onView(note) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content
                ,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(note) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Note")
                }
                IconButton(onClick = { onDelete(note) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Note")
                }
            }
        }
    }
}
