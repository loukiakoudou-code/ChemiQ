package com.lkountoura.chemiq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lkountoura.chemiq.navigation.routes.Home
import com.lkountoura.chemiq.navigation.routes.Matching
import com.lkountoura.chemiq.navigation.routes.PeriodicTable
import com.lkountoura.chemiq.navigation.routes.Question
import com.lkountoura.chemiq.navigation.routes.Results
import com.lkountoura.chemiq.navigation.routes.TimeAttack
import com.lkountoura.chemiq.navigation.routes.TopicSelect
import com.lkountoura.chemiq.ui.screen.Home.HomeScreen
import com.lkountoura.chemiq.ui.screen.MatchingScreen
import com.lkountoura.chemiq.ui.screen.QuestionScreen
import com.lkountoura.chemiq.ui.screen.QuizSelectionScreen
import com.lkountoura.chemiq.ui.screen.ResultScreen
import com.lkountoura.chemiq.ui.screen.TimeAttackScreen
import com.lkountoura.chemiq.viewmodel.HomeNavigation
import com.lkountoura.chemiq.viewmodel.MatchingNavigation
import com.lkountoura.chemiq.viewmodel.QuestionNavigation
import com.lkountoura.chemiq.viewmodel.ResultNavigation
import com.lkountoura.chemiq.viewmodel.TimeAttackNavigation


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

                        HomeNavigation.TimeAttack ->
                            navController.navigate(TimeAttack)

                        HomeNavigation.Matching ->
                            navController.navigate(Matching)
                    }
                }
            )
        }

        composable<TopicSelect> {
            QuizSelectionScreen(
                onBackPressed = {
                    navController.popBackStack()
                }, onStart = { unitId, difficulty, unitTitle ->
                    navController.navigate(
                        Question(
                            topicId = unitId,
                            difficulty = difficulty,
                            unitTitle = unitTitle
                        )
                    )
                }
            )
        }

        composable<Question> {
            QuestionScreen(
                onNavigate = { event ->
                    when (event) {
                        QuestionNavigation.Back ->
                            navController.popBackStack<Question>(inclusive = true)

                        QuestionNavigation.End ->
                            navController.navigate(Results) {
                                popUpTo<Question> { inclusive = true }
                            }
                    }
                }
            )
        }

        composable<TimeAttack> {
            TimeAttackScreen(
                onNavigate = { event ->
                    when (event) {
                        TimeAttackNavigation.Back ->
                            navController.popBackStack<Home>(inclusive = false)

                        TimeAttackNavigation.End ->
                            navController.navigate(Results) {
                                popUpTo<TimeAttack> { inclusive = true }
                            }
                    }
                }
            )
        }

        composable<Results> {
            ResultScreen(
                onNavigate = { event ->
                    when (event) {
                        ResultNavigation.Home ->
                            navController.navigate(Home) {
                                navController.popBackStack<Home>(inclusive = false)
                            }

                        ResultNavigation.PlayAgain ->
                            navController.popBackStack<TopicSelect>(inclusive = false)

                        ResultNavigation.Review -> {
//                            // οθόνη Επανάληψης (Figma 08) – προς το παρόν δεν κάνει τίποτα
                        }
                    }
                }
            )
        }
        composable<Matching> {
            MatchingScreen(
                onNavigate = { event ->
                    when (event) {
                        MatchingNavigation.Back ->
                            navController.popBackStack<Home>(inclusive = false)

                        MatchingNavigation.End ->
                            navController.navigate(Results) {
                                popUpTo<Matching> { inclusive = true }
                            }
                    }
                }
            )
        }
    }
}

