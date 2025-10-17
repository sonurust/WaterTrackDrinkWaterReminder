package store.radhasingh.watertrackdrinkwaterreminder.ui.screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val userSettings by viewModel.userSettings.collectAsStateWithLifecycle()

    var dailyGoal by remember { mutableStateOf(2000) }
    var reminderInterval by remember { mutableStateOf(60) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD),
                        Color(0xFFBBDEFB)
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1976D2)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Daily Goal Setting
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Daily Goal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Set your daily water intake goal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { 
                                if (dailyGoal > 500) {
                                    dailyGoal -= 250
                                    viewModel.updateDailyGoal(dailyGoal)
                                }
                            },
                            enabled = dailyGoal > 500,
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("-")
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Text(
                            text = "${dailyGoal}ml",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2),
                            modifier = Modifier.weight(1f)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Button(
                            onClick = { 
                                if (dailyGoal < 5000) {
                                    dailyGoal += 250
                                    viewModel.updateDailyGoal(dailyGoal)
                                }
                            },
                            enabled = dailyGoal < 5000,
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("+")
                        }
                    }
                }
            }

            // Reminder Interval Setting
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Reminder Interval",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "How often to remind you to drink water",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { 
                                if (reminderInterval > 15) {
                                    reminderInterval -= 15
                                    viewModel.updateReminderInterval(reminderInterval)
                                }
                            },
                            enabled = reminderInterval > 15,
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("-")
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Text(
                            text = "${reminderInterval} min",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2),
                            modifier = Modifier.weight(1f)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Button(
                            onClick = { 
                                if (reminderInterval < 240) {
                                    reminderInterval += 15
                                    viewModel.updateReminderInterval(reminderInterval)
                                }
                            },
                            enabled = reminderInterval < 240,
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("+")
                        }
                    }
                }
            }

            // Notification Settings
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Notifications",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Enable Notifications
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Enable Notifications",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { 
                                notificationsEnabled = it
                                viewModel.updateNotificationsEnabled(it)
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Sound
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sound",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = { 
                                soundEnabled = it
                                viewModel.updateSoundEnabled(it)
                            },
                            enabled = notificationsEnabled
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Vibration
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Vibration",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Switch(
                            checked = vibrationEnabled,
                            onCheckedChange = { 
                                vibrationEnabled = it
                                viewModel.updateVibrationEnabled(it)
                            },
                            enabled = notificationsEnabled
                        )
                    }
                }
            }
        }
    }
}
