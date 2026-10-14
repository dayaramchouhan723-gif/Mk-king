package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MemoriesScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.VisionScanScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.KyraBlue
import com.example.ui.theme.KyraCyan
import com.example.ui.theme.CyberRed
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.MyraGreen
import com.example.ui.theme.MyraRed
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MainViewModel

enum class NavigationDestination(val title: String) {
    HOME("Home"),
    SCAN("Scan"),
    MEMORIES("Memories"),
    CHAT("Chat"),
    SETTINGS("Settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val mainViewModel: MainViewModel = viewModel()
                MainAppContainer(viewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun MainAppContainer(viewModel: MainViewModel) {
    var currentDestination by remember { mutableStateOf(NavigationDestination.HOME) }

    // Intercept back button to return to HOME when on sub-screens
    BackHandler(enabled = currentDestination != NavigationDestination.HOME) {
        currentDestination = NavigationDestination.HOME
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = DarkBackground,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .background(DarkSurface)
                    .border(width = 1.dp, color = DarkCardBorder)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = DarkSurface,
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = currentDestination == NavigationDestination.HOME,
                    onClick = { currentDestination = NavigationDestination.HOME },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            fontSize = 11.sp,
                            fontWeight = if (currentDestination == NavigationDestination.HOME) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = MyraRed,
                        indicatorColor = MyraRed,
                        unselectedIconColor = Color(0xFF888898),
                        unselectedTextColor = Color(0xFF888898)
                    ),
                    modifier = Modifier.testTag("nav_home")
                )

                NavigationBarItem(
                    selected = currentDestination == NavigationDestination.SCAN,
                    onClick = { currentDestination = NavigationDestination.SCAN },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.DocumentScanner,
                            contentDescription = "Scan"
                        )
                    },
                    label = {
                        Text(
                            text = "Scan",
                            fontSize = 11.sp,
                            fontWeight = if (currentDestination == NavigationDestination.SCAN) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = KyraCyan,
                        indicatorColor = KyraBlue,
                        unselectedIconColor = Color(0xFF888898),
                        unselectedTextColor = Color(0xFF888898)
                    ),
                    modifier = Modifier.testTag("nav_scan")
                )

                NavigationBarItem(
                    selected = currentDestination == NavigationDestination.MEMORIES,
                    onClick = { currentDestination = NavigationDestination.MEMORIES },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Memories"
                        )
                    },
                    label = {
                        Text(
                            text = "Memories",
                            fontSize = 11.sp,
                            fontWeight = if (currentDestination == NavigationDestination.MEMORIES) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = CyberRed,
                        indicatorColor = CyberRed,
                        unselectedIconColor = Color(0xFF888898),
                        unselectedTextColor = Color(0xFF888898)
                    ),
                    modifier = Modifier.testTag("nav_memories")
                )

                NavigationBarItem(
                    selected = currentDestination == NavigationDestination.CHAT,
                    onClick = { currentDestination = NavigationDestination.CHAT },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Forum,
                            contentDescription = "Chat"
                        )
                    },
                    label = {
                        Text(
                            text = "Chat",
                            fontSize = 11.sp,
                            fontWeight = if (currentDestination == NavigationDestination.CHAT) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = SkyBlue,
                        indicatorColor = SkyBlue,
                        unselectedIconColor = Color(0xFF888898),
                        unselectedTextColor = Color(0xFF888898)
                    ),
                    modifier = Modifier.testTag("nav_chat")
                )

                NavigationBarItem(
                    selected = currentDestination == NavigationDestination.SETTINGS,
                    onClick = { currentDestination = NavigationDestination.SETTINGS },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    },
                    label = {
                        Text(
                            text = "Settings",
                            fontSize = 11.sp,
                            fontWeight = if (currentDestination == NavigationDestination.SETTINGS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = SkyBlue,
                        indicatorColor = SkyBlue,
                        unselectedIconColor = Color(0xFF888898),
                        unselectedTextColor = Color(0xFF888898)
                    ),
                    modifier = Modifier.testTag("nav_settings")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentDestination) {
                NavigationDestination.HOME -> HomeScreen(
                    viewModel = viewModel,
                    onNavigateToChat = { currentDestination = NavigationDestination.CHAT }
                )
                NavigationDestination.SCAN -> VisionScanScreen(viewModel = viewModel)
                NavigationDestination.MEMORIES -> MemoriesScreen(viewModel = viewModel)
                NavigationDestination.CHAT -> ChatScreen(viewModel = viewModel)
                NavigationDestination.SETTINGS -> SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
