package store.radhasingh.watertrackdrinkwaterreminder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Energy Blue color constants
val EnergyBlue = Color(0xFF00E5FF)
val LightBlueGlow = Color(0xFF80D8FF)
val TextPrimary = Color(0xFFB3E5FC)
val TextSecondary = Color(0xFF81D4FA)

// Set of Material typography styles with glowing effects
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.8f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 20f
        )
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.7f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 16f
        )
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.6f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 12f
        )
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.5f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 8f
        )
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.4f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 6f
        )
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.3f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 4f
        )
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.4f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 6f
        )
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.3f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 4f
        )
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = EnergyBlue.copy(alpha = 0.2f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 3f
        )
    )
)