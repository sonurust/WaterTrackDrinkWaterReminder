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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.VolumeUp
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
import store.radhasingh.watertrackdrinkwaterreminder.ui.viewmodel.SettingsViewModel
import store.radhasingh.watertrackdrinkwaterreminder.utils.PermissionUtils
import store.radhasingh.watertrackdrinkwaterreminder.ui.components.SoundSelectionCard
import store.radhasingh.watertrackdrinkwaterreminder.ui.components.SoundOption
import store.radhasingh.watertrackdrinkwaterreminder.audio.SoundManager
import store.radhasingh.watertrackdrinkwaterreminder.audio.SoundType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val userSettings by viewModel.userSettings.collectAsStateWithLifecycle()
    
    // Initialize SoundManager
    val soundManager = remember { SoundManager(context) }

    var dailyGoal by remember { mutableStateOf(2000) }
    var reminderInterval by remember { mutableStateOf(60) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var hasNotificationPermission by remember { mutableStateOf(false) }
    var voiceEnabled by remember { mutableStateOf(true) }
    var ambientSoundEnabled by remember { mutableStateOf(false) }
    
    // Sound options
    val notificationSoundOptions = listOf(
        SoundOption("soft_water_reminder", "Soft Water Reminder", "Gentle water drop with calm chime", SoundType.NOTIFICATION),
        SoundOption("hydration_bell", "Hydration Bell", "Light bell with ripple echo effect", SoundType.NOTIFICATION)
    )
    
    val buttonSoundOptions = listOf(
        SoundOption("button_tap", "Button Tap", "Soft tap with water bubble tone", SoundType.BUTTON_TAP),
        SoundOption("add_glass_confirmed", "Add Glass", "Bubbly pop with gentle chime", SoundType.ADD_GLASS)
    )
    
    val goalSoundOptions = listOf(
        SoundOption("goal_achieved", "Goal Achieved", "Melodic sparkle for goal completion", SoundType.GOAL_ACHIEVED),
        SoundOption("goal_complete_voice", "Voice Celebration", "Voice message for goal completion", SoundType.GOAL_COMPLETE)
    )

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }

    // Check permission status
    LaunchedEffect(Unit) {
        hasNotificationPermission = PermissionUtils.hasNotificationPermission(context)
    }

    // Update local state when userSettings changes
    LaunchedEffect(userSettings) {
        userSettings?.let { settings ->
            dailyGoal = settings.dailyGoal
            reminderInterval = settings.reminderIntervalMinutes
            soundEnabled = settings.soundEnabled
            vibrationEnabled = settings.vibrationEnabled
            notificationsEnabled = settings.notificationsEnabled
            voiceEnabled = settings.voiceEnabled
            ambientSoundEnabled = settings.ambientSoundEnabled
        }
    }
    
    // Cleanup SoundManager when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
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
            // Notification Permission Status
            if (PermissionUtils.shouldRequestNotificationPermission()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (hasNotificationPermission) Color(0xFFE8F5E8) else Color(0xFFFFEBEE)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (hasNotificationPermission) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = "Permission Status",
                            tint = if (hasNotificationPermission) Color(0xFF4CAF50) else Color(0xFFFF5722),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (hasNotificationPermission) "Notifications Enabled" else "Notification Permission Required",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = if (hasNotificationPermission) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                            )
                            Text(
                                text = if (hasNotificationPermission) 
                                    "You'll receive water reminder notifications" 
                                else 
                                    "Allow notifications to receive water reminders",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (hasNotificationPermission) Color(0xFF388E3C) else Color(0xFFE53935)
                            )
                        }
                        if (!hasNotificationPermission) {
                            Button(
                                onClick = {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                            ) {
                                Text("Allow", color = Color.White)
                            }
                        }
                    }
                }
            }
            
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
                    
                    Column {
                        Text(
                            text = "${dailyGoal}ml",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = dailyGoal.toFloat(),
                            onValueChange = { newValue ->
                                dailyGoal = newValue.toInt()
                                viewModel.updateDailyGoal(dailyGoal)
                            },
                            valueRange = 500f..5000f,
                            steps = 17, // 4500 / 250 = 18 steps
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF1976D2),
                                activeTrackColor = Color(0xFF1976D2),
                                inactiveTrackColor = Color(0xFFE0E0E0)
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "500ml",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = "5000ml",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
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
                    
                    Column {
                        Text(
                            text = "${reminderInterval} min",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = reminderInterval.toFloat(),
                            onValueChange = { newValue ->
                                reminderInterval = newValue.toInt()
                                viewModel.updateReminderInterval(reminderInterval)
                            },
                            valueRange = 15f..240f,
                            steps = 14, // 225 / 15 = 15 steps
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF1976D2),
                                activeTrackColor = Color(0xFF1976D2),
                                inactiveTrackColor = Color(0xFFE0E0E0)
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "15 min",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = "4 hours",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Notification Settings",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1976D2)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Customize how and when you receive water reminders",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Enable Notifications
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Enable Water Reminders",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Receive notifications to stay hydrated",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Notification Sound",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Play sound when reminder arrives",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Vibration Alert",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Vibrate device for silent reminders",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = vibrationEnabled,
                            onCheckedChange = { 
                                vibrationEnabled = it
                                viewModel.updateVibrationEnabled(it)
                            },
                            enabled = notificationsEnabled
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Notification Preview Button
                    if (notificationsEnabled && hasNotificationPermission) {
                        Button(
                            onClick = {
                                // Show a preview notification
                                viewModel.showNotificationPreview()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1976D2)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Preview Notification",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to see how your water reminder notification will look",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            // Enhanced Sound Settings
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Sound Settings",
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sound Settings",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1976D2)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Customize sounds for different app interactions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Voice Prompts
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Voice Prompts",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Enable voice messages for motivation",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = voiceEnabled,
                            onCheckedChange = { 
                                voiceEnabled = it
                                viewModel.updateVoiceEnabled(it)
                            },
                            enabled = soundEnabled
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Ambient Sounds
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Ambient Background",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Play calming background sounds",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = ambientSoundEnabled,
                            onCheckedChange = { 
                                ambientSoundEnabled = it
                                viewModel.updateAmbientSoundEnabled(it)
                            },
                            enabled = soundEnabled
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Sound Selection Cards
                    if (soundEnabled) {
                        Text(
                            text = "Sound Selection",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1976D2)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Notification Sound Selection
                        SoundSelectionCard(
                            title = "Notification Sound",
                            description = "Sound played for water reminders",
                            currentSound = userSettings?.selectedNotificationSound ?: "soft_water_reminder",
                            soundOptions = notificationSoundOptions,
                            onSoundSelected = { soundName ->
                                viewModel.updateNotificationSound(soundName)
                            },
                            onPreviewSound = { soundType ->
                                soundManager.playSoundPreview(soundType)
                            },
                            enabled = soundEnabled && notificationsEnabled
                        )
                        
                        // Button Sound Selection
                        SoundSelectionCard(
                            title = "Button Sound",
                            description = "Sound played when tapping buttons",
                            currentSound = userSettings?.selectedButtonSound ?: "button_tap",
                            soundOptions = buttonSoundOptions,
                            onSoundSelected = { soundName ->
                                viewModel.updateButtonSound(soundName)
                            },
                            onPreviewSound = { soundType ->
                                soundManager.playSoundPreview(soundType)
                            },
                            enabled = soundEnabled
                        )
                        
                        // Goal Sound Selection
                        SoundSelectionCard(
                            title = "Goal Achievement Sound",
                            description = "Sound played when reaching daily goal",
                            currentSound = userSettings?.selectedGoalSound ?: "goal_achieved",
                            soundOptions = goalSoundOptions,
                            onSoundSelected = { soundName ->
                                viewModel.updateGoalSound(soundName)
                            },
                            onPreviewSound = { soundType ->
                                soundManager.playSoundPreview(soundType)
                            },
                            enabled = soundEnabled
                        )
                    }
                }
            }
        }
    }
}
