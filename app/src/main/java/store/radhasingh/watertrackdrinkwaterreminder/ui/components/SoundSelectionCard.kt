package store.radhasingh.watertrackdrinkwaterreminder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import store.radhasingh.watertrackdrinkwaterreminder.audio.SoundManager
import store.radhasingh.watertrackdrinkwaterreminder.audio.SoundType

@Composable
fun SoundSelectionCard(
    title: String,
    description: String,
    currentSound: String,
    soundOptions: List<SoundOption>,
    onSoundSelected: (String) -> Unit,
    onPreviewSound: (SoundType) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) Color.White else Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = enabled) { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Sound",
                    tint = if (enabled) Color(0xFF1976D2) else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = if (enabled) Color.Black else Color.Gray
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                if (enabled) {
                    IconButton(
                        onClick = { 
                            val soundType = getSoundTypeFromName(currentSound)
                            onPreviewSound(soundType)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Preview",
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            if (expanded && enabled) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ) {
                    items(soundOptions) { option ->
                        SoundOptionItem(
                            option = option,
                            isSelected = option.name == currentSound,
                            onSelected = { 
                                onSoundSelected(option.name)
                                expanded = false
                            },
                            onPreview = { onPreviewSound(option.soundType) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SoundOptionItem(
    option: SoundOption,
    isSelected: Boolean,
    onSelected: () -> Unit,
    onPreview: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() }
            .background(
                if (isSelected) Color(0xFFE3F2FD) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = option.displayName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF1976D2) else Color.Black
            )
            Text(
                text = option.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        IconButton(onClick = onPreview) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Preview ${option.displayName}",
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

private fun getSoundTypeFromName(soundName: String): SoundType {
    return when (soundName) {
        "soft_water_reminder" -> SoundType.NOTIFICATION
        "hydration_bell" -> SoundType.NOTIFICATION
        "goal_achieved" -> SoundType.GOAL_ACHIEVED
        "button_tap" -> SoundType.BUTTON_TAP
        "add_glass_confirmed" -> SoundType.ADD_GLASS
        "app_startup" -> SoundType.APP_STARTUP
        "daily_motivation" -> SoundType.DAILY_MOTIVATION
        "goal_complete_voice" -> SoundType.GOAL_COMPLETE
        "hydration_focus_loop" -> SoundType.HYDRATION_FOCUS
        "morning_reminder_music" -> SoundType.MORNING_REMINDER
        "evening_relax_music" -> SoundType.EVENING_RELAX
        else -> SoundType.NOTIFICATION
    }
}

data class SoundOption(
    val name: String,
    val displayName: String,
    val description: String,
    val soundType: SoundType
)
