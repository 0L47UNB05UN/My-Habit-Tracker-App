package com.example.myhabittrackerapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.WaterDrop
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.model.HabitType
import com.example.myhabittrackerapp.model.Habits
import com.example.myhabittrackerapp.ui.theme.MyHabitTrackerAppTheme
import com.example.myhabittrackerapp.ui.theme.spacing

@Composable
fun DiscoverScreen(
    onHabitClick: (Habits) -> Unit,
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
            HeaderSection()
            // Content Area
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 88.dp),
                contentPadding = PaddingValues(horizontal = spacing.xLarge, vertical = spacing.large),
                verticalArrangement = Arrangement.spacedBy(spacing.xLarge)
            ) {
                // Health Essentials Section
                item {
                    HabitSection()
                }
                item {
                    HealthHabitsGrid({ onHabitClick(it) })
                }
                // Mindfulness Section
                item {
                    Text(
                        text = "Mindfulness",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                item {
                    MindfulnessCard({onHabitClick(it)})
                }

                // Fixed grid for mindfulness habits - NO nested LazyVerticalGrid
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(spacing.large)
                    ) {
                        // First row of mindfulness habits
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(spacing.large)
                        ) {
                            // Gratitude Journal
                            Box(modifier = Modifier.weight(1f)) {
                                HabitTile(
                                    icon = Icons.Outlined.AutoStories,
                                    onHabitClick = { onHabitClick(it) },
                                    title = "Gratitude Journal",
                                    subtitle = "Write 3 things",
                                    iconColor = Color(0xFF818CF8),
                                    iconBackgroundColor = Color(0xFF818CF8).copy(alpha = 0.1f)
                                )
                            }
                            // Deep Breath
                            Box(modifier = Modifier.weight(1f)) {
                                HabitTile(
                                    icon = Icons.Outlined.Air,
                                    onHabitClick = onHabitClick,
                                    title = "Deep Breath",
                                    subtitle = "Box breathing",
                                    iconColor = Color(0xFF34D399),
                                    iconBackgroundColor = Color(0xFF34D399).copy(alpha = 0.1f)
                                )
                            }
                        }
                    }
                }

                // Productivity Section
                item {
                    Text(
                        text = "Productivity",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = spacing.medium)
                    )
                }

                item {
                    ProductivityList(onHabitClick)
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.xLarge, vertical = spacing.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Discover",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Start something new today.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(spacing.medium))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(spacing.medium)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = "Auto Awesome",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun HabitSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Health Essentials",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Suggested",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun HealthHabitsGrid(
    onHabitClick: (Habits) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.large)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.large)
        ) {
            // Drink Water
            HabitTile(
                modifier = Modifier.weight(1f),
                onHabitClick = onHabitClick,
                icon = Icons.Outlined.WaterDrop,
                title = "Drink Water",
                subtitle = "Daily hydration goal",
                iconColor = MaterialTheme.colorScheme.primary,
                iconBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )

            // Morning Stretch
            HabitTile(
                modifier = Modifier.weight(1f),
                onHabitClick = onHabitClick,
                icon = Icons.Outlined.FitnessCenter,
                title = "Morning Stretch",
                subtitle = "10 min flexibility",
                iconColor = Color(0xFFFF9F80),
                iconBackgroundColor = Color(0xFFFF9F80).copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
private fun HabitTile(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    iconBackgroundColor: Color,
    onHabitClick: (Habits) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(spacing.medium))
            .scale(if (isPressed) 0.95f else 1f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onHabitClick(
                            Habits(
                                title, subtitle, HabitType.Start, 
                                Color.Green, icon
                            )
                        )
                    }
                )
            },
        shape = RoundedCornerShape(spacing.medium),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp) // Fixed height
                .padding(spacing.large),
            verticalArrangement = Arrangement.SpaceBetween // Distribute space evenly
        ) {
            // Top section
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(spacing.small))
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Middle section
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Bottom section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun MindfulnessCard(
    onHabitClick: (Habits ) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(RoundedCornerShape(spacing.medium))
            .clickable { }
    ) {
        // Background Image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        )

        // Overlays
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize().clickable(
                    onClick = {
                        onHabitClick(
                            Habits(
                                "Deep Meditation",
                                "Unlock mental clarity"
                            )
                        )
                    }
                )
                .padding(spacing.large),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Deep Meditation",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Unlock mental clarity",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        // Add Button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(spacing.large)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun ProductivityList(
    onHabitClick: (Habits) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        // Deep Work Item
        ProductivityItem(
            icon = Icons.Outlined.Timer,
            title = "Deep Work",
            subtitle = "90 min focus session",
            iconColor = MaterialTheme.colorScheme.secondary,
            iconBackgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
            onHabitClick = onHabitClick
        )

        // Read 10 Pages Item
        ProductivityItem(
            icon = Icons.Outlined.Book,
            title = "Read 10 Pages",
            subtitle = "Expand your knowledge",
            iconColor = MaterialTheme.colorScheme.tertiary,
            iconBackgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            onHabitClick = onHabitClick
        )
    }
}

@Composable
private fun ProductivityItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    iconBackgroundColor: Color,
    onHabitClick: (Habits) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(spacing.medium))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(spacing.medium)
            ).clickable(
                onClick = {
                    onHabitClick(
                        Habits(
                            title, subtitle, HabitType.Start, iconColor,
                            icon
                        )
                    )
                }
            )
            .padding(spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(spacing.small))
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(spacing.large))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Single preview that uses your actual theme
@Preview(
    name = "Discover Screen",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6"
)
@Composable
fun DiscoverScreenPreview() {
    MyHabitTrackerAppTheme {
        DiscoverScreen(){}
    }
}