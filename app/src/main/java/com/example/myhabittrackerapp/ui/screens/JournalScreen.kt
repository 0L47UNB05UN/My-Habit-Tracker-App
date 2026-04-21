package com.example.myhabittrackerapp.ui.screens

import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarMonth
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.model.JournalEntry
import com.example.myhabittrackerapp.ui.theme.spacing
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

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
            JournalHeader(
                habitName = habitName,
                date = appViewModel.today,
                searchQuery = appViewModel.searchQuery,
                onSearchQueryChange = { appViewModel.searchQuery = it },
                onSettingsClick = onSettingsClick,
                isCalendarView = appViewModel.isCalendarView,
                onViewToggle = { appViewModel.isCalendarView = it }
            )
            
            Crossfade(targetState = appViewModel.isCalendarView, label = "view_fade") { isCalendar ->
                if (isCalendar) {
                    HabitCalendarView(
                        entries = journalEntries,
                        today = appViewModel.today
                    )
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 88.dp),
                        contentPadding = PaddingValues(horizontal = spacing.large, vertical = spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(spacing.large)
                    ) {
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
                                onEditClick = { appViewModel.startEditing(entry) }
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
    }
}

@Composable
private fun JournalHeader(
    habitName: String, 
    date: LocalDate,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSettingsClick: () -> Unit,
    isCalendarView: Boolean,
    onViewToggle: (Boolean) -> Unit
) {
    var isSearchVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.large, vertical = spacing.medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${date.month.name} ${date.year}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
            
            // View Switcher
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = { onViewToggle(false) },
                    modifier = Modifier.size(32.dp).background(if (!isCalendarView) Color.White else Color.Transparent, CircleShape)
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.List, 
                        contentDescription = "List",
                        modifier = Modifier.size(18.dp),
                        tint = if (!isCalendarView) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                IconButton(
                    onClick = { onViewToggle(true) },
                    modifier = Modifier.size(32.dp).background(if (isCalendarView) Color.White else Color.Transparent, CircleShape)
                ) {
                    Icon(
                        Icons.Outlined.CalendarMonth, 
                        contentDescription = "Calendar",
                        modifier = Modifier.size(18.dp),
                        tint = if (isCalendarView) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isSearchVisible) {
                Text(
                    text = habitName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            } else {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier.weight(1f).height(56.dp),
                    placeholder = { Text("Search reflections...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { 
                            onSearchQueryChange("")
                            isSearchVisible = false 
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close search")
                        }
                    }
                )
            }
            
            Row {
                if (!isSearchVisible) {
                    IconButton(onClick = { isSearchVisible = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    }
}

@Composable
fun HabitCalendarView(
    entries: List<JournalEntry>,
    today: LocalDate
) {
    // Generate dates for current month
    val firstDayOfMonth = LocalDate(today.year, today.month, 1)
    val lastDayOfMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
    
    val daysInMonth = (1..lastDayOfMonth.dayOfMonth).map { day ->
        LocalDate(today.year, today.month, day)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.large)
    ) {
        // Weekday Headers (Starting from Sunday)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            // First day offset (0 = Monday, 6 = Sunday)
            // Adjusting for Sunday start: Sunday should be 0.
            val firstDayOffset = (firstDayOfMonth.dayOfWeek.ordinal + 1) % 7
            
            items(firstDayOffset) {
                Spacer(Modifier.fillMaxWidth())
            }

            items(daysInMonth) { date ->
                val entry = entries.find { it.date == date }
                val isToday = date == today
                
                CalendarDayItem(
                    date = date,
                    entry = entry,
                    isToday = isToday
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(Color(0xFF4CAF50), "Success")
            Spacer(modifier = Modifier.width(spacing.large))
            LegendItem(Color.Red, "Missed")
            Spacer(modifier = Modifier.width(spacing.large))
            LegendItem(Color.LightGray.copy(alpha = 0.3f), "Future")
        }
    }
}

@Composable
fun CalendarDayItem(
    date: LocalDate,
    entry: JournalEntry?,
    isToday: Boolean
) {
    val backgroundColor = when {
        entry == null -> Color.White
        entry.isCompleted -> Color(0xFFE8F5E9)
        else -> Color(0xFFFFEBEE)
    }
    
    val indicatorColor = when {
        entry == null -> Color.Transparent
        entry.isCompleted -> Color(0xFF4CAF50)
        else -> Color.Red
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = if (isToday) 2.dp else 0.dp,
                color = if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 14.sp,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
                color = if (isToday) MaterialTheme.colorScheme.primary else Color.Black
            )
            if (entry != null) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(indicatorColor)
                )
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
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
