package com.example.myhabittrackerapp.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.myhabittrackerapp.model.ThemeClass
import com.example.myhabittrackerapp.model.UserSettings
import com.example.myhabittrackerapp.ui.theme.spacing

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        SettingsContent(
            settings = uiState,
            onUserNameChange = viewModel::updateUserName,
            onThemeChange = viewModel::updateTheme,
            onThemeClassChange = viewModel::updateThemeClass,
            onDailyNudgeChange = viewModel::updateDailyNudge,
            onMonthlyRecapChange = viewModel::updateMonthlyRecap,
            onReminderTimeClick = { /* Show time picker dialog */ },
            onExportClick = viewModel::exportData,
            onClearDataClick = viewModel::clearAllData
        )
    }
}

@Composable
fun SettingsContent(
    settings: UserSettings,
    onUserNameChange: (String) -> Unit,
    onThemeChange: (Boolean) -> Unit,
    onThemeClassChange: (ThemeClass)->Unit,
    onDailyNudgeChange: (Boolean) -> Unit,
    onMonthlyRecapChange: (Boolean) -> Unit,
    onReminderTimeClick: () -> Unit,
    onExportClick: () -> Unit,
    onClearDataClick: () -> Unit
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
            SettingsTopBar()
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
                item {
                    ProfileCard(
                        userName = settings.userName,
                        onNameChange = onUserNameChange
                    )
                }
                item {
                    AppearanceSection(
                        onThemeClassChange = onThemeClassChange,
                        onThemeChange = onThemeChange,
                        themeClass = settings.themeClass
                    )
                }
                item {
                    NotificationsSection(
                        dailyNudgeEnabled = settings.dailyNudgeEnabled,
                        onDailyNudgeChange = onDailyNudgeChange,
                        monthlyRecapEnabled = settings.monthlyRecapEnabled,
                        onMonthlyRecapChange = onMonthlyRecapChange,
                        reminderTime = settings.reminderTime,
                        onReminderTimeClick = onReminderTimeClick
                    )
                }
                item {
                    DataPrivacySection(
                        onExportClick = onExportClick,
                        onClearDataClick = onClearDataClick
                    )
                }
                item {
                    SettingsFooter()
                }
            }
        }
    }
}

@Composable
private fun SettingsTopBar() {
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
            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(40.dp))
        }
    }
}

@Composable
private fun ProfileCard(
    userName: String,
    onNameChange: (String) -> Unit
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
            // Header image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    repeat(5) { index ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(2.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.1f + (index * 0.05f)
                                    ),
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-24).dp)
                    .padding(horizontal = spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )
                            .border(
                                width = 4.dp,
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName.take(1).uppercase(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            )
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = "Change photo",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing.small))
                BasicTextField(
                    value = userName,
                    onValueChange = onNameChange,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            innerTextField()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Personalizing your local journal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AppearanceSection(
    themeClass: ThemeClass,
    onThemeClassChange: (ThemeClass) -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Text(
            text = "APPEARANCE",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.5.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val systemDefault: Boolean = isSystemInDarkTheme()
            ThemeOption(
                isSelected = themeClass.name == "System",
                onClick = { Log.d("mine", "$themeClass");
                    onThemeChange(systemDefault); onThemeClassChange(ThemeClass.System) },
                previewContent = {
                    Column(
                        modifier = Modifier
                            .height(64.dp)
                            .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp, 8.dp)
                                .background(Color(0xFFE0E0E0), RoundedCornerShape(2.dp))
                        )
                        Box(
                            modifier = Modifier
                                .size(48.dp, 8.dp)
                                .background(Color(0xFFD0D0D0), RoundedCornerShape(2.dp))
                        )
                    }
                },
                label = "System Default"
            )
            ThemeOption(
                isSelected = themeClass.name == "Light",
                onClick = { onThemeChange(false); onThemeClassChange(ThemeClass.Light) },
                previewContent = {
                    Column(
                        modifier = Modifier
                            .height(64.dp)
                            .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp, 8.dp)
                                .background(Color(0xFFE0E0E0), RoundedCornerShape(2.dp))
                        )
                        Box(
                            modifier = Modifier
                                .size(48.dp, 8.dp)
                                .background(Color(0xFFD0D0D0), RoundedCornerShape(2.dp))
                        )
                    }
                },
                label = "Light"
            )
            ThemeOption(
                isSelected = themeClass.name == "Dark",
                onClick = { onThemeChange(true); onThemeClassChange(ThemeClass.Dark) },
                previewContent = {
                    Column(
                        modifier = Modifier
                            .height(64.dp)
                            .background(Color(0xFF1A1A1A), RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp, 8.dp)
                                .background(Color(0xFF2A2A2A), RoundedCornerShape(2.dp))
                        )
                        Box(
                            modifier = Modifier
                                .size(48.dp, 8.dp)
                                .background(Color(0xFF333333), RoundedCornerShape(2.dp))
                        )
                    }
                },
                label = "Dark"
            )
        }
    }
}

@Composable
private fun ThemeOption(
    isSelected: Boolean,
    onClick: () -> Unit,
    previewContent: @Composable () -> Unit,
    label: String
) {
    Card(
        modifier = Modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            previewContent()
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun NotificationsSection(
    dailyNudgeEnabled: Boolean,
    onDailyNudgeChange: (Boolean) -> Unit,
    monthlyRecapEnabled: Boolean,
    onMonthlyRecapChange: (Boolean) -> Unit,
    reminderTime: String,
    onReminderTimeClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Text(
            text = "REMINDERS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.5.sp
        )

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
                // Daily Nudge
                NotificationRow(
                    icon = Icons.Outlined.Notifications,
                    title = "Daily Nudge",
                    subtitle = "Get a ping to write your daily entry",
                    trailing = {
                        Switch(
                            checked = dailyNudgeEnabled,
                            onCheckedChange = onDailyNudgeChange,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                        )
                    },
                    showDivider = true
                )

                // Reminder Time
                NotificationRow(
                    icon = Icons.Outlined.Schedule,
                    title = "Reminder Time",
                    trailing = {
                        Surface(
                            shape = RoundedCornerShape(spacing.small),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable { onReminderTimeClick() }
                        ) {
                            Text(
                                text = reminderTime,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(
                                    horizontal = spacing.medium,
                                    vertical = 4.dp
                                )
                            )
                        }
                    },
                    showDivider = true
                )

                // Monthly Recap
                NotificationRow(
                    icon = Icons.Outlined.Summarize,
                    title = "Monthly Recap",
                    subtitle = "Alert when your summary is ready",
                    trailing = {
                        Switch(
                            checked = monthlyRecapEnabled,
                            onCheckedChange = onMonthlyRecapChange,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                        )
                    },
                    showDivider = false
                )
            }
        }
    }
}

@Composable
private fun NotificationRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    trailing: @Composable () -> Unit,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.large),
            horizontalArrangement = Arrangement.spacedBy(spacing.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(spacing.small))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // Trailing content
            trailing()
        }
        if (showDivider) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                modifier = Modifier.padding(horizontal = spacing.large)
            )
        }
    }
}

@Composable
private fun DataPrivacySection(
    onExportClick: () -> Unit,
    onClearDataClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Text(
            text = "DATA & PRIVACY",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.5.sp
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(spacing.medium),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large),
                verticalArrangement = Arrangement.spacedBy(spacing.large)
            ) {
                // Warning message
                Surface(
                    shape = RoundedCornerShape(spacing.small),
                    color = Color(0xFFFFF8E1).copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.medium),
                        horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B)
                        )

                        Text(
                            text = "Your data is stored locally. If you delete the app or clear cache, your journals will be permanently lost. We recommend periodic exports.",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = Color(0xFFB45309)
                        )
                    }
                }
                Button(
                    onClick = onExportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(spacing.small),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CloudDownload,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(spacing.small))
                    Text(
                        text = "Export as CSV/JSON",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClearDataClick() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Clear All Local Data",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.xLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.xLarge)
        ) {
            Text(
                text = "Privacy Policy",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable { }
            )
            Text(
                text = "Terms of Service",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable { }
            )
            Text(
                text = "Support",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable { }
            )
        }

        Text(
            text = "VERSION 2.4.0 (BUILD 42)",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            letterSpacing = 0.5.sp
        )
    }
}

// Preview
//@Preview
//@Composable
//fun SettingsScreenPreview() {
//    MyHabitTrackerAppTheme {
//        SettingsScreen()
//    }
//}