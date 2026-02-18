package com.example.myhabittrackerapp.ui.screens

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.DoNotDisturbOn
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.ui.theme.MyHabitTrackerAppTheme
import com.example.myhabittrackerapp.ui.theme.spacing


@Composable
fun MyHabitsScreen() {
    var selectedTab by remember { mutableStateOf(Tab.Today) }

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
            // Tabs
            MyHabitsTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
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
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(spacing.large)
                    ) {
                        // Drink Water
                        HabitCard(
                            icon = Icons.Outlined.WaterDrop,
                            title = "Drink Water",
                            subtitle = "Daily goal: 8 glasses",
                            color = MaterialTheme.colorScheme.primary,
                            toStart = true
                        )
                        // Morning Meditate
                        HabitCard(
                            icon = Icons.Outlined.SelfImprovement,
                            title = "Morning Meditate",
                            subtitle = "10 mins • 4/7 days streak",
                            color = MaterialTheme.colorScheme.primary,
                            toStart = true
                        )
                    }
                }
                // To Stop Section
                item {
                    HabitSection(
                        title = "To Stop",
                        icon = Icons.Outlined.DoNotDisturbOn,
                        iconColor = MaterialTheme.colorScheme.error
                    )
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(spacing.large)
                    ) {
                        // Late Night Snacking
                        HabitCard(
                            icon = Icons.Outlined.Fastfood,
                            title = "Late Night Snacking",
                            subtitle = "Avoid after 9:00 PM",
                            color = Color.Red,
                            toStart = false
                        )
                        // Excessive Social Media
                        HabitCard(
                            icon = Icons.Outlined.Smartphone,
                            title = "Excessive Social Media",
                            subtitle = "Limit to 30 mins daily",
                            color = Color.Red,
                            toStart = false
                        )
                    }
                }
            }
        }
        FloatingAddButton()
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
                text = "3 habits remaining for today",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun MyHabitsTabs(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.xLarge)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(0.dp)
            )
    ) {
        Tab.entries.forEach { tab ->
            TabItem(
                title = tab.title,
                isSelected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(top = spacing.medium)
                .background(
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Transparent
                )
        )
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
    toStart: Boolean
){
    Card(
        modifier = Modifier.fillMaxWidth().height(85.dp),
        shape = RoundedCornerShape(spacing.xLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(spacing.large),
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
                Column {
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
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){Icon(
                    imageVector = if (toStart) Icons.Filled.CheckCircle else Icons.Filled.Block,
                    contentDescription = null,
                    modifier = Modifier.padding(end=spacing.large)
                        .requiredSize(spacing.xLarge),
                    tint = color
                )}
            }
        }
    }
}

@Composable
private fun FloatingAddButton() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
            .padding(horizontal = spacing.xLarge),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(spacing.xLarge),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primary
//            )
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

enum class Tab(val title: String) {
    Today("Today"),
    AllHabits("All Habits")
}

// Preview
@Preview
@Composable
fun MyHabitsScreenPreview() {
    MyHabitTrackerAppTheme(
        darkTheme = true
    ) {
        MyHabitsScreen()
    }
}