package store.radhasingh.watertrackdrinkwaterreminder.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeManager {
    var isDarkTheme by mutableStateOf(true) // Default to dark theme
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }

    fun setDarkTheme(isDark: Boolean) {
        isDarkTheme = isDark
    }
}
