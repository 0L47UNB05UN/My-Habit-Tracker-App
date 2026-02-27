package com.example.myhabittrackerapp.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.model.HabitType
import com.example.myhabittrackerapp.ui.theme.spacing

@Composable
fun HabitSettingsScreen(
    appViewModel: HabitScreenSettingsViewModel,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Header
            HabitSettingsHeader(
                onBackClick = onBackClick,
                onSaveClick = { appViewModel.save(); onBackClick() }
            )
            // Main Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 88.dp),
                contentPadding = PaddingValues(
                    horizontal = spacing.large,
                    vertical = spacing.large
                ),
                verticalArrangement = Arrangement.spacedBy(spacing.xLarge)
            ) {
                // Habit Name Section
                item {
                    HabitNameSection(
                        habitName = appViewModel.currentHabit.title,
                        onNameChange = { appViewModel.onHabitNameChange(it)  },
                        flag = true
                    )
                    HabitNameSection(
                        habitName = appViewModel.currentHabit.subtitle,
                        onNameChange = { appViewModel.onSubtitleChange(it) },
                        flag = false
                    )
                }
                // Habit Type Section
                item {
                    HabitTypeSection(
                        selectedType = appViewModel.currentHabit.habitType,
                        onTypeChange = { appViewModel.onHabitTypeChange(it)  }
                    )
                }
                // Reminders Card
                item {
                    RemindersCard(
                        dailyReminderEnabled = appViewModel.dailyReminderEnabled,
                        onDailyReminderChange = { appViewModel.dailyReminderEnabled = it },
                        reminderTime = appViewModel.reminderTime,
                        onReminderTimeClick = {  },
                        frequency = appViewModel.frequency,
                        onFrequencyClick = {  }
                    )
                }
                // Danger Zone
                item {
                    DangerZone(
                        onDeleteClick = { appViewModel.delete(); onBackClick() }
                    )
                }
            }
        }
        HomeIndicator()
    }
}

@Composable
private fun HabitSettingsHeader(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = spacing.large,
                    vertical = spacing.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onBackClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = "Back",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "Habit Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Save",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onSaveClick() }
            )
        }
    }
}

@Composable
private fun HabitNameSection(
    habitName: String,
    onNameChange: (String) -> Unit,
    flag: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        Text(
            text = if (flag) "HABIT NAME" else "HABIT DESCRIPTION",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            letterSpacing = 0.5.sp
        )

        BasicTextField(
            value = habitName,
            onValueChange = onNameChange,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(spacing.medium))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(spacing.medium)
                        )
                        .padding(spacing.large)
                ) {
                    if (habitName.isEmpty()) {
                        Text(
                            text = "e.g. Morning Yoga",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HabitTypeSection(
    selectedType: HabitType,
    onTypeChange: (HabitType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        Text(
            text = "GOAL TYPE",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            letterSpacing = 0.5.sp
        )

        // Segmented Control
        Surface(
            shape = RoundedCornerShape(spacing.medium),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                HabitType.entries.forEach { type ->
                    val isSelected = selectedType == type
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(spacing.small))
                            .background(
                                if (isSelected)
                                    MaterialTheme.colorScheme.surface
                                else
                                    Color.Transparent
                            )
                            .clickable { onTypeChange(type) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = type.displayName,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Text(
            text = "Choose \"Start\" for positive routines and \"Stop\" for breaking bad habits.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun RemindersCard(
    dailyReminderEnabled: Boolean,
    onDailyReminderChange: (Boolean) -> Unit,
    reminderTime: String,
    onReminderTimeClick: () -> Unit,
    frequency: String,
    onFrequencyClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.NotificationsActive,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Daily Reminder",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Remind me to log progress",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Custom iOS-style toggle
                IOSSwitch(
                    checked = dailyReminderEnabled,
                    onCheckedChange = onDailyReminderChange
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                modifier = Modifier.padding(horizontal = spacing.large)
            )

            // Time Picker Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large)
                    .clickable { onReminderTimeClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notification Time",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Surface(
                    shape = RoundedCornerShape(spacing.small),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clickable { onReminderTimeClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = spacing.medium,
                            vertical = spacing.small
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = reminderTime.split(" ")[0],
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = " ${reminderTime.split(" ")[1]}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                modifier = Modifier.padding(horizontal = spacing.large)
            )

            // Frequency Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large)
                    .clickable { onFrequencyClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Frequency",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = frequency,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clickable { onFrequencyClick() }
                )
            }
        }
    }
}

@Composable
private fun IOSSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 44.dp, height = 24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (checked)
                    MaterialTheme.colorScheme.primary
                else
                    Color(0xFFD1D1D6)
            )
            .clickable { onCheckedChange(!checked) }
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .offset(x = if (checked) 20.dp else 2.dp)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFD1D1D6),
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun DangerZone(
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.xLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFFEF4444)
            ),
            shape = CircleShape,
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFEF4444).copy(alpha = 0.3f)
            ),
            modifier = Modifier.padding(horizontal = spacing.xLarge)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(spacing.small))
            Text(
                text = "Delete Habit",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "This will remove all history for this habit.\nThis action cannot be undone.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun HomeIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 4.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        )
    }
}