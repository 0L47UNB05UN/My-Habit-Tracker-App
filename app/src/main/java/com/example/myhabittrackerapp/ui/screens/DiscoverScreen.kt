package com.example.myhabittrackerapp.ui.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhabittrackerapp.ui.theme.MyHabitTrackerAppTheme
import com.example.myhabittrackerapp.ui.theme.spacing

@Composable
fun DiscoverScreen() {
    var selectedCategory by remember { mutableStateOf("Health") }
    val categories = listOf("Health", "Mindfulness", "Productivity", "Social")
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

            // Search Input
            SearchSection()

            // Categories Tab Bar
            CategoryTabs(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

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
                    HabitSection(
                        title = "Health Essentials",
                        badge = "Suggested"
                    )
                }

                item {
                    HealthHabitsGrid()
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
                    MindfulnessCard()
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
                    ProductivityList()
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
private fun SearchSection() {
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.xLarge, vertical = spacing.medium)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Type a custom habit...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(spacing.medium)),
                shape = RoundedCornerShape(spacing.medium),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                ),
                singleLine = true
            )

            // Add Button
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = spacing.medium)
                    .size(40.dp)
                    .clip(RoundedCornerShape(spacing.small))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.xLarge, vertical = spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(spacing.xLarge)
    ) {
        items(categories) { category ->
            CategoryTab(
                title = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
private fun CategoryTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onClick() }
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
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
    badge: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (badge != null) {
            Text(
                text = badge,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun HealthHabitsGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.large)
        ) {
            // Drink Water
            Box(modifier = Modifier.weight(1f)) {
                HabitTile(
                    icon = Icons.Outlined.WaterDrop,
                    title = "Drink Water",
                    subtitle = "Daily hydration goal",
                    iconColor = MaterialTheme.colorScheme.primary,
                    iconBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            }
            // Morning Stretch
            Box(modifier = Modifier.weight(1f)) {
                HabitTile(
                    icon = Icons.Outlined.FitnessCenter,
                    title = "Morning Stretch",
                    subtitle = "10 min flexibility",
                    iconColor = Color(0xFFFF9F80),
                    iconBackgroundColor = Color(0xFFFF9F80).copy(alpha = 0.1f)
                )
            }
        }
    }
}

@Composable
private fun HabitTile(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    iconBackgroundColor: Color
) {
    var isPressed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(spacing.medium))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(spacing.medium)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
            .scale(if (isPressed) 0.95f else 1f)
            .padding(spacing.large)
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

        Spacer(modifier = Modifier.height(spacing.medium))

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

@Composable
private fun MindfulnessCard() {
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
                .fillMaxSize()
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
private fun ProductivityList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        // Deep Work Item
        ProductivityItem(
            icon = Icons.Outlined.Timer,
            title = "Deep Work",
            subtitle = "90 min focus session",
            iconColor = MaterialTheme.colorScheme.secondary,
            iconBackgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
        )

        // Read 10 Pages Item
        ProductivityItem(
            icon = Icons.Outlined.Book,
            title = "Read 10 Pages",
            subtitle = "Expand your knowledge",
            iconColor = MaterialTheme.colorScheme.tertiary,
            iconBackgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
        )
    }
}

@Composable
private fun ProductivityItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    iconBackgroundColor: Color
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

@Composable
private fun BottomNavigationBar() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.xLarge),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .width(screenWidth * 0.9f)
                .height(64.dp),
            shape = RoundedCornerShape(spacing.large),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem("Journal", isSelected = false)
                BottomNavItem("Discover", isSelected = true)
                BottomNavItem("Summary", isSelected = false)
                BottomNavItem("Settings", isSelected = false)
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Help,
                contentDescription = label,
                tint = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-4).dp)
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            letterSpacing = 0.5.sp
        )
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
        DiscoverScreen()
    }
}