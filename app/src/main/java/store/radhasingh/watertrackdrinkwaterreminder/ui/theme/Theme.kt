package store.radhasingh.watertrackdrinkwaterreminder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF), // Energy Blue
    secondary = Color(0xFF80D8FF), // Light Blue Glow
    tertiary = Color(0xFF40E0D0), // Turquoise accent
    background = Color(0xFF0A1929), // Deep Blue Background
    surface = Color(0xFF102A43), // Card Surface
    onPrimary = Color(0xFF000000), // Black text on energy blue
    onSecondary = Color(0xFF000000), // Black text on light blue
    onTertiary = Color(0xFF000000), // Black text on turquoise
    onBackground = Color(0xFFB3E5FC), // Text Primary (light cyan)
    onSurface = Color(0xFF81D4FA), // Text Secondary (cyan)
    surfaceVariant = Color(0xFF1A2332), // Dark surface variant
    onSurfaceVariant = Color(0xFF4FC3F7), // Light cyan text
    error = Color(0xFFFF6B6B), // Energy red for errors
    onError = Color(0xFF000000) // Black text on error
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2), // Blue for light theme
    secondary = Color(0xFF00ACC1), // Teal
    tertiary = Color(0xFF4CAF50), // Green
    background = Color(0xFFF5F5F5), // Light background
    surface = Color(0xFFFFFFFF), // White surface
    onPrimary = Color(0xFFFFFFFF), // White text on blue
    onSecondary = Color(0xFFFFFFFF), // White text on teal
    onTertiary = Color(0xFFFFFFFF), // White text on green
    onBackground = Color(0xFF1C1B1F), // Dark text on light background
    onSurface = Color(0xFF1C1B1F), // Dark text on white surface
    surfaceVariant = Color(0xFFE3F2FD), // Light blue surface variant
    onSurfaceVariant = Color(0xFF424242) // Dark gray text
)

@Composable
fun WaterTrackDrinkWaterReminderTheme(
    darkTheme: Boolean = true, // Default to dark theme
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic colors to use our custom theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}