package com.example.myhabittrackerapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.model.JournalEntry
import com.example.myhabittrackerapp.ui.theme.spacing
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@Composable
fun JournalScreen(
    appViewModel: JournalScreenViewModel,
    onSettingsClick: () -> Unit = {}
) {
    val journalEntries by appViewModel.journalEntries.collectAsState()
    val habitName by appViewModel.currentHabitName.collectAsState()
    val isEditing = appViewModel.editingEntryId != null
    val hasEntryForToday = journalEntries.any { it.date == appViewModel.today }
    
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Scroll to top when editing starts
    LaunchedEffect(isEditing) {
        if (isEditing) {
            listState.animateScrollToItem(0)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFAF7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            JournalHeader(habitName, appViewModel.today, onSettingsClick)
            
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 88.dp),
                contentPadding = PaddingValues(horizontal = spacing.large, vertical = spacing.medium),
                verticalArrangement = Arrangement.spacedBy(spacing.large)
            ) {
                // Show input card if no entry for today OR if we are currently editing an entry
                if (!hasEntryForToday || isEditing) {
                    item(key = "add_journal_card") {
                        AddJournalCard(
                            currentEntry = appViewModel.currentEntry,
                            onEntryChange = { appViewModel.currentEntry = it },
                            isCompleted = appViewModel.isCompleted,
                            onToggleCompletion = { appViewModel.isCompleted = it },
                            onSave = { appViewModel.save() },
                            date = appViewModel.editingDate ?: appViewModel.today,
                            isEditing = isEditing,
                            onCancel = { appViewModel.cancelEditing() }
                        )
                    }
                }

                items(journalEntries, key = { it.id }) { entry ->
                    JournalEntryCard(
                        entry = entry,
                        onEditClick = {
                            appViewModel.startEditing(entry)
                        }
                    )
                }

                if (journalEntries.isEmpty() && !isEditing) {
                    item(key = "empty_state") {
                        EmptyStateCard()
                    }
                }
            }
        }
    }
}

@Composable
private fun JournalHeader(
    habitName: String, 
    date: LocalDate,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.large, vertical = spacing.medium)
    ) {
        Text(
            text = "${date.month.name} ${date.year}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            letterSpacing = 1.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habitName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Row {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    }
}

@Composable
private fun AddJournalCard(
    currentEntry: String,
    onEntryChange: (String) -> Unit,
    isCompleted: Boolean,
    onToggleCompletion: (Boolean) -> Unit,
    onSave: () -> Unit,
    date: LocalDate,
    isEditing: Boolean = false,
    onCancel: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = if (isEditing) 2.dp else 1.dp, 
                    color = if (isEditing) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.LightGray.copy(alpha = 0.5f)
                ), 
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isEditing) 4.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(spacing.large)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = date.dayOfMonth.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = if (isEditing) date.dayOfWeek.name.take(3) else "TODAY", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(spacing.medium))
                
                IconButton(
                    onClick = { onToggleCompletion(true) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                        contentDescription = "Succeeded",
                        tint = if (isCompleted) Color(0xFF4CAF50) else Color.LightGray
                    )
                }
                IconButton(
                    onClick = { onToggleCompletion(false) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Failed",
                        tint = if (!isCompleted) Color.Red else Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.width(spacing.large))

            Column(modifier = Modifier.weight(1f)) {
                if (isEditing) {
                    Text(
                        text = "EDITING REFLECTION",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                BasicTextField(
                    value = currentEntry,
                    onValueChange = onEntryChange,
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.heightIn(min = 100.dp)) {
                            if (currentEntry.isEmpty()) {
                                Text(
                                    "How did today go? Tap to write your summary...",
                                    color = Color.LightGray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacing.medium))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onSave,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A6572))
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.Add, 
                            contentDescription = null, 
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isEditing) "Update Entry" else "Add Entry")
                    }
                    
                    if (isEditing) {
                        OutlinedButton(
                            onClick = onCancel,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun JournalEntryCard(
    entry: JournalEntry,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(spacing.large)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = entry.date.dayOfMonth.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = entry.date.dayOfWeek.name.take(3), fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(spacing.medium))
                
                Icon(
                    imageVector = if (entry.isCompleted) Icons.Filled.CheckCircle else Icons.Filled.Circle,
                    contentDescription = null,
                    tint = if (entry.isCompleted) Color(0xFF4CAF50) else Color.LightGray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(spacing.large))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (entry.isCompleted) Color(0xFFFFF9E6) else Color(0xFFFFEBEE)
                    ) {
                        Text(
                            text = if (entry.isCompleted) "⭐ PERFECT DAY" else "PARTIAL",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (entry.isCompleted) Color(0xFFD4AF37) else Color.Red,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(text = "9:30 PM", fontSize = 10.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(spacing.small))

                Text(
                    text = entry.content,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(spacing.medium))

                Button(
                    onClick = {
                        onEditClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5), contentColor = Color.Gray),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Edit Reflection", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun EmptyStateCard() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("No journal history for this habit.", color = Color.Gray)
    }
}
