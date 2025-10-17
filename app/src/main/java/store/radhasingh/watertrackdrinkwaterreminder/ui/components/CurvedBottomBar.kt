package store.radhasingh.watertrackdrinkwaterreminder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import store.radhasingh.watertrackdrinkwaterreminder.ui.theme.EnergyBlue
import store.radhasingh.watertrackdrinkwaterreminder.ui.theme.LightBlueGlow

@Composable
fun CurvedBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // Home Tab
            IconButton(
                onClick = { onTabSelected(0) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedTab == 0) EnergyBlue else LightBlueGlow,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            // Stats Tab
            IconButton(
                onClick = { onTabSelected(1) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Stats",
                    tint = if (selectedTab == 1) EnergyBlue else LightBlueGlow,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            // Settings Tab
            IconButton(
                onClick = { onTabSelected(2) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = if (selectedTab == 2) EnergyBlue else LightBlueGlow,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
