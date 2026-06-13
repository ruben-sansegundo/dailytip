package com.example.dailytip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.dailytip.ui.navigation.DailyTipNavGraph
import com.example.dailytip.ui.theme.DailytipTheme
import com.example.dailytip.ui.viewmodel.TipViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as DailyTipApplication
        val viewModel: TipViewModel by viewModels {
            TipViewModel.factory(app.repository, app.dailyTipManager)
        }

        setContent {
            DailytipTheme {
                val navController = rememberNavController()
                DailyTipNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
