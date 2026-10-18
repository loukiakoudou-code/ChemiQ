package com.lkountoura.chemiq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lkountoura.chemiq.navigation.routes.Home
import com.lkountoura.chemiq.navigation.routes.PeriodicTable
import com.lkountoura.chemiq.navigation.routes.Question
import com.lkountoura.chemiq.navigation.routes.TopicSelect
import com.lkountoura.chemiq.ui.screen.Home.HomeScreen
import com.lkountoura.chemiq.ui.screen.QuestionScreen
import com.lkountoura.chemiq.ui.screen.QuizSelectionScreen
import com.lkountoura.chemiq.viewmodel.HomeNavigation


@Composable
fun ChemiQNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        composable<Home> {
            HomeScreen(
                onNavigate = { event ->
                    when (event) {
                        HomeNavigation.ToTopicSelect ->
                            navController.navigate(TopicSelect(game = "quiz"))

                        HomeNavigation.ToPeriodicTable ->
                            navController.navigate(PeriodicTable)
                    }
                }
            )
        }

        composable<TopicSelect> {
            QuizSelectionScreen(
                onBackPressed = {
                    navController.popBackStack()
                }, onStart = { unitId, difficulty ->
                    navController.navigate(Question(topicId = unitId, difficulty = difficulty))
                }
            )
        }
        composable<Question> {
            QuestionScreen()
        }

    }

}

