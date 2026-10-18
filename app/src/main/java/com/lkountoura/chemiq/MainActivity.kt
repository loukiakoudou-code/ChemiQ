package com.lkountoura.chemiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lkountoura.chemiq.navigation.ChemiQNavHost
import com.lkountoura.chemiq.ui.component.common.ChemiQBottomBar
import com.lkountoura.chemiq.ui.theme.ChemiQTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            ChemiQTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { ChemiQBottomBar(navController) }
                ) { innerPadding ->
                    ChemiQNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

