package store.radhasingh.watertrackdrinkwaterreminder.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import store.radhasingh.watertrackdrinkwaterreminder.ui.components.EnergyCard
import store.radhasingh.watertrackdrinkwaterreminder.ui.theme.ThemeManager
import store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel.SettingsViewModel
import store.radhasingh.watertrackdrinkwaterreminder.utils.PermissionUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val userSettings by viewModel.userSettings.collectAsStateWithLifecycle()

    var dailyGoal by remember { mutableStateOf(2000) }
    var reminderInterval by remember { mutableStateOf(60) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    var hasNotificationPermission by remember { mutableStateOf(PermissionUtils.hasNotificationPermission(context)) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            hasNotificationPermission = isGranted
            if (isGranted) {
                viewModel.updateNotificationsEnabled(true)
            }
        }
    )

    // Update local state when userSettings changes
    LaunchedEffect(userSettings) {
        userSettings?.let { settings ->
            dailyGoal = settings.dailyGoal
            reminderInterval = settings.reminderIntervalMinutes
            soundEnabled = settings.soundEnabled
            vibrationEnabled = settings.vibrationEnabled
            notificationsEnabled = settings.notificationsEnabled
        }
    }

    // Re-check permission status if the screen recomposes or context changes
    DisposableEffect(context) {
        hasNotificationPermission = PermissionUtils.hasNotificationPermission(context)
        onDispose { /* No specific cleanup needed here */ }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
        TopAppBar(
            title = { Text("Settings", color = MaterialTheme.colorScheme.onPrimary) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Notification Permission Status
            if (PermissionUtils.shouldRequestNotificationPermission()) {
                EnergyCard(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Notification Permission",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (hasNotificationPermission) "Granted" else "Denied",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (hasNotificationPermission) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                            )
                        }
                        if (!hasNotificationPermission) {
                            Button(
                                onClick = {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Request Permission", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }
            }

            // Daily Goal Setting
            SettingItem(title = "Daily Water Goal (ml)") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { if (dailyGoal > 100) dailyGoal -= 100 },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("-")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "${dailyGoal}ml",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { dailyGoal += 100 },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("+")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Reminder Interval Setting
            SettingItem(title = "Reminder Interval (minutes)") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { if (reminderInterval > 15) reminderInterval -= 15 },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("-")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "${reminderInterval} min",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { reminderInterval += 15 },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("+")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Sound Toggle
            SettingToggle(
                title = "Sound Enabled",
                checked = soundEnabled,
                onCheckedChange = { soundEnabled = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Vibration Toggle
            SettingToggle(
                title = "Vibration Enabled",
                checked = vibrationEnabled,
                onCheckedChange = { vibrationEnabled = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Notifications Toggle
            SettingToggle(
                title = "Notifications Enabled",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Theme Toggle
            SettingToggle(
                title = "Dark Theme",
                checked = ThemeManager.isDarkTheme,
                onCheckedChange = { ThemeManager.setTheme(it) }
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    viewModel.updateDailyGoal(dailyGoal)
                    viewModel.updateReminderInterval(reminderInterval)
                    viewModel.updateSoundEnabled(soundEnabled)
                    viewModel.updateVibrationEnabled(vibrationEnabled)
                    viewModel.updateNotificationsEnabled(notificationsEnabled)
                    onBackClick() // Go back after saving
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Settings", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Reset Data Button
            Button(
                onClick = {
                    // Implement reset data logic in ViewModel
                    viewModel.resetData()
                    onBackClick() // Go back after resetting
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Reset All Data", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

@Composable
fun SettingItem(title: String, content: @Composable () -> Unit) {
    EnergyCard {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun SettingToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    EnergyCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}