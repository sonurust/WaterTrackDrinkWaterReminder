package store.radhasingh.watertrackdrinkwaterreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import store.radhasingh.watertrackdrinkwaterreminder.di.databaseModule
import store.radhasingh.watertrackdrinkwaterreminder.di.repositoryModule
import store.radhasingh.watertrackdrinkwaterreminder.di.viewModelModule
import store.radhasingh.watertrackdrinkwaterreminder.di.notificationModule
import store.radhasingh.watertrackdrinkwaterreminder.ui.navigation.WaterTrackNavigation
import store.radhasingh.watertrackdrinkwaterreminder.ui.theme.WaterTrackDrinkWaterReminderTheme

class MainActivity : ComponentActivity() {
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
        
        // Check if we should open AddGlass screen from notification
        val shouldOpenAddGlass = intent.getBooleanExtra("open_add_glass", false)
        val startDestination = if (shouldOpenAddGlass) "add_glass" else "home"
        
        setContent {
            WaterTrackDrinkWaterReminderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WaterTrackNavigation(startDestination = startDestination)
                }
            }
        }
    }
}