package store.radhasingh.watertrackdrinkwaterreminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import store.radhasingh.watertrackdrinkwaterreminder.di.databaseModule
import store.radhasingh.watertrackdrinkwaterreminder.di.repositoryModule
import store.radhasingh.watertrackdrinkwaterreminder.di.viewModelModule
import store.radhasingh.watertrackdrinkwaterreminder.di.notificationModule
import store.radhasingh.watertrackdrinkwaterreminder.ui.navigation.BottomNavigationBar
import store.radhasingh.watertrackdrinkwaterreminder.ui.theme.ThemeManager
import store.radhasingh.watertrackdrinkwaterreminder.ui.theme.WaterTrackDrinkWaterReminderTheme

class MainActivity : ComponentActivity() {
    
    private var hasNotificationPermission by mutableStateOf(false)
    
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
                notificationModule
            )
        }
        
        enableEdgeToEdge()
        
        // Check notification permission
        checkNotificationPermission()
        
        setContent {
            WaterTrackDrinkWaterReminderTheme(
                darkTheme = ThemeManager.isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigationBar()
                }
            }
        }
    }
    
    private fun checkNotificationPermission() {
        hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Permission not required for older versions
        }
        
        if (!hasNotificationPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}