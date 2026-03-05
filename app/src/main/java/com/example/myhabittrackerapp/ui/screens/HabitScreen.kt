package com.example.myhabittrackerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DoNotDisturbOn
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.model.HabitType
import com.example.myhabittrackerapp.model.Habits
import com.example.myhabittrackerapp.ui.theme.spacing


@Composable
fun MyHabitsScreen(
    appViewModel: HabitScreenSettingsViewModel,
    onNavigate: () -> Unit = {},
    onAddHabitClick: () -> Unit = {},
    onCheckClick: (Long) -> Unit = {}
) {
    val habits by appViewModel.habits.collectAsState()

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
            MyHabitsHeader()
            // Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 140.dp),
                contentPadding = PaddingValues(
                    horizontal = spacing.large,
                    vertical = spacing.medium
                ),
                verticalArrangement = Arrangement.spacedBy(spacing.xLarge)
            ) {
                // To Start Section
                item {
                    HabitSection(
                        title = "To Start",
                        icon = Icons.Outlined.AddCircle,
                        iconColor = MaterialTheme.colorScheme.primary
                    )
                }
                
                items(habits.filter { it.habitType == HabitType.Start }) { habit ->
                    HabitCard(
                        title = habit.title,
                        subtitle = habit.subtitle,
                        color = Color(habit.colorArgb),
                        habitType = habit.habitType,
                        icon = Icons.Outlined.WaterDrop, // Temporary fix for ImageVector
                        onNavigate = {
                            appViewModel.markCurrentHabit(habit)
                            onNavigate()
                        },
                        onCheckClick = { onCheckClick(habit.id) }
                    )
                }

                item {
                    HabitSection(
                        title = "To Stop",
                        icon = Icons.Outlined.DoNotDisturbOn,
                        iconColor = MaterialTheme.colorScheme.error
                    )
                }

                items(habits.filter { it.habitType == HabitType.Stop }) { habit ->
                    HabitCard(
                        title = habit.title,
                        subtitle = habit.subtitle,
                        color = Color(habit.colorArgb),
                        habitType = habit.habitType,
                        icon = Icons.Outlined.DoNotDisturbOn, // Temporary fix
                        onNavigate = {
                            appViewModel.markCurrentHabit(habit)
                            onNavigate()
                        },
                        onCheckClick = { onCheckClick(habit.id) }
                    )
                }
            }
        }
        FloatingAddButton({appViewModel.resetCurrentHabit()}, onAddHabitClick)
    }
}

@Composable
private fun MyHabitsHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = spacing.xLarge,
                    vertical = spacing.large
                )
        ) {
            Text(
                text = "My Habits",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = spacing.small)
            )
            Text(
                text = "Track your progress today",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun HabitSection(
    title: String,
    icon: ImageVector,
    iconColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.small),
        modifier = Modifier.padding(horizontal = spacing.small)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun HabitCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    habitType: HabitType,
    onNavigate: () -> Unit,
    onCheckClick: () -> Unit
){
    Card(
        onClick = onNavigate,
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp),
        shape = RoundedCornerShape(spacing.xLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Left border indicator
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(8.dp)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                        )
                )
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(spacing.small))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Text content
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Left
                    )
                }
                
                // Check Action
                Box(
                    modifier = Modifier
                        .padding(end = spacing.large)
                        .size(48.dp)
                        .clickable { onCheckClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (habitType == HabitType.Start) Icons.Filled.CheckCircle else Icons.Filled.Block,
                        contentDescription = "Check Habit",
                        modifier = Modifier.size(32.dp),
                        tint = color
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingAddButton(
    onClick: () -> Unit,
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
            .padding(horizontal = spacing.xLarge),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { onClick(); onNavigate()},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(spacing.xLarge)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(spacing.small))
            Text(
                text = "Add New Habit",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
