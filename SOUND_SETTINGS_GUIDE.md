# 🔊 Sound Settings Implementation Guide

## Overview
The WaterTrack app now includes comprehensive sound settings that allow users to customize their audio experience. Users can enable/disable sounds, select different sounds for various interactions, and preview sounds before applying them.

## 🎵 Available Sounds

### Notification Sounds
- **Soft Water Reminder** - Gentle water drop with calm chime (2 seconds)
- **Hydration Bell** - Light bell with ripple echo effect (3 seconds)

### UI Interaction Sounds
- **Button Tap** - Soft tap with water bubble tone (1 second)
- **Add Glass Confirmed** - Bubbly pop with gentle chime (1.5 seconds)

### Goal Achievement Sounds
- **Goal Achieved** - Melodic sparkle for goal completion (3 seconds)
- **Voice Celebration** - Voice message for goal completion

### Voice Prompts
- **Daily Motivation** - "Hey there! Time to drink some water and stay hydrated."
- **Goal Complete Voice** - "Great job! You've reached your hydration goal for today!"

### Ambient Background Sounds
- **Hydration Focus Loop** - Calm ambient with water flowing sounds (5 seconds, loopable)
- **Morning Reminder Music** - Light upbeat tune with piano (5 seconds, loopable)
- **Evening Relax Music** - Warm ambient with ocean waves (5 seconds, loopable)

### App Sounds
- **App Startup** - Water wave transition with sparkle (1.5 seconds)

## 🛠️ Implementation Details

### SoundManager Class
The `SoundManager` class handles all audio playback in the app:
- Uses `SoundPool` for short sound effects
- Supports volume control and sound preview
- Manages sound resources efficiently
- Automatically respects user sound preferences

### User Settings Model
Extended `UserSettings` with new fields:
```kotlin
val selectedNotificationSound: String = "soft_water_reminder"
val selectedButtonSound: String = "button_tap"
val selectedGoalSound: String = "goal_achieved"
val voiceEnabled: Boolean = true
val ambientSoundEnabled: Boolean = false
```

### Settings UI Components
- **SoundSelectionCard** - Expandable card for sound selection
- **Sound preview functionality** - Tap play button to preview sounds
- **Real-time sound testing** - Test sounds before applying settings

## 🎛️ User Controls

### Master Sound Toggle
- **Enable/Disable All Sounds** - Master switch for all audio
- When disabled, all sound options become unavailable

### Individual Sound Categories
1. **Notification Sound** - Choose sound for water reminders
2. **Button Sound** - Choose sound for UI interactions
3. **Goal Achievement Sound** - Choose sound for goal completion
4. **Voice Prompts** - Enable/disable voice messages
5. **Ambient Background** - Enable/disable background sounds

### Sound Preview
- Tap the play button next to any sound option to preview it
- Preview works even when sounds are disabled
- Helps users choose their preferred sounds

## 🔧 Integration Points

### HomeViewModel Integration
```kotlin
// Initialize sound manager
viewModel.initializeSoundManager(context)

// Play sounds on user actions
viewModel.playButtonTapSound()
viewModel.addDrinkEntry(drinkType, volume) // Plays add glass sound + goal achievement if applicable
```

### SettingsViewModel Integration
```kotlin
// Update sound preferences
viewModel.updateNotificationSound("hydration_bell")
viewModel.updateButtonSound("add_glass_confirmed")
viewModel.updateVoiceEnabled(true)
```

## 📁 File Structure
```
app/src/main/
├── java/.../audio/
│   └── SoundManager.kt
├── java/.../ui/components/
│   └── SoundSelectionCard.kt
├── java/.../ui/screen/
│   └── SettingsScreen.kt (enhanced)
└── res/raw/
    ├── soft_water_reminder.mp3
    ├── hydration_bell.mp3
    ├── button_tap.mp3
    ├── add_glass_confirmed.mp3
    ├── goal_achieved.mp3
    ├── daily_motivation.mp3
    ├── goal_complete_voice.mp3
    ├── app_startup.mp3
    ├── hydration_focus_loop.mp3
    ├── morning_reminder_music.mp3
    └── evening_relax_music.mp3
```

## 🎯 Usage Examples

### Basic Sound Integration
```kotlin
// In your composable
val soundManager = remember { SoundManager(context) }
val userSettings by viewModel.userSettings.collectAsStateWithLifecycle()

// Play sound on button click
Button(
    onClick = {
        soundManager.playButtonTapSound(userSettings ?: UserSettings())
        // Your button action
    }
) {
    Text("Add Water")
}
```

### Sound Preview in Settings
```kotlin
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
    }
)
```

## 🚀 Future Enhancements
- Custom sound upload functionality
- Sound volume controls per category
- Time-based sound scheduling (different sounds for morning/evening)
- Sound themes (nature, urban, minimal, etc.)
- Integration with system volume controls

## 📱 User Experience
- **Intuitive Interface** - Easy-to-use sound selection cards
- **Instant Preview** - Test sounds before applying
- **Persistent Settings** - Preferences saved automatically
- **Graceful Degradation** - App works perfectly without sounds
- **Accessibility** - Respects system sound preferences
