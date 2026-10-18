package com.lkountoura.chemiq.ui.component.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lkountoura.chemiq.R
import com.lkountoura.chemiq.navigation.routes.Home
import com.lkountoura.chemiq.navigation.routes.Progress
import com.lkountoura.chemiq.navigation.routes.Review
import com.lkountoura.chemiq.navigation.routes.Settings

enum class BottomTab(
    val route: Any,
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int,
) {
    HOME(Home, R.string.tab_home, R.drawable.ic_home),
    REVIEW(Review, R.string.tab_review, R.drawable.ic_review),
    PROGRESS(Progress, R.string.tab_progress, R.drawable.ic_progress),
    SETTINGS(Settings, R.string.tab_settings, R.drawable.ic_settings),
}

@Composable
fun ChemiQBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    val showBar = BottomTab.entries.any { destination?.hasRoute(it.route::class) == true }

    AnimatedVisibility(
        visible = showBar,
        enter = slideInVertically(animationSpec = tween(300)) { it } + fadeIn(tween(300)),
        exit = slideOutVertically(animationSpec = tween(300)) { it } + fadeOut(tween(300))
    ) {
        NavigationBar {
            BottomTab.entries.forEach { tab ->
                NavigationBarItem(
                    selected = destination?.hierarchy?.any { it.hasRoute(tab.route::class) } == true,
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.label)) },
                )
            }
        }
    }
}