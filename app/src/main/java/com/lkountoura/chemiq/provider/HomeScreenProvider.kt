package com.lkountoura.chemiq.provider

import androidx.compose.ui.graphics.Color
import com.lkountoura.chemiq.R
import com.lkountoura.chemiq.ui.component.homePage.GamePanelState
import com.lkountoura.chemiq.ui.component.homePage.GameTabState
import com.lkountoura.chemiq.ui.component.homePage.HeaderState
import com.lkountoura.chemiq.ui.component.homePage.ReviewBannerState
import com.lkountoura.chemiq.ui.state.HomeScreenState
import javax.inject.Inject

class HomeScreenProvider @Inject constructor() {

    fun provideData(reviewQuestionsCount: Int = 0): HomeScreenState.Data {

        val quiz = GameTabState(tag = "quiz").setGameState(
            title = "Quiz",
            description = "10 ερωτήσεις ανά ενότητα",
            boxColor = Color(0xFFEEEAFE),
            iconColor = Color(0xFF5B3DF5),
            icon = R.drawable.ic_flask,
        )

        val timeAttack = GameTabState(tag = "time_attack").setGameState(
            title = "Time Attack",
            description = "60 δευτερόλεπτα",
            boxColor = Color(0xFFFFEDDF),
            iconColor = Color(0xFFF97316),
            icon = R.drawable.ic_timer,
        )

        val periodic = GameTabState(tag = "periodic").setGameState(
            title = "Περιοδικός Πίνακας",
            description = "Στοιχεία Z = 1–36",
            boxColor = Color(0xFFDDF6F4),
            iconColor = Color(0xFF0EA5A0),
            icon = R.drawable.ic_periodic,
        )

        val balance = GameTabState(tag = "balance").setGameState(
            title = "Ισοστάθμιση",
            description = "Συντελεστές εξισώσεων",
            boxColor = Color(0xFFE3EEFE),
            iconColor = Color(0xFF2F7BF6),
            icon = R.drawable.ic_scale,
        )

        val match = GameTabState(tag = "match").setGameState(
            title = "Αντιστοίχιση",
            description = "Ονόματα και χημικοί τύποι",
            boxColor = Color(0xFFFDE7F0),
            iconColor = Color(0xFFE8478B),
            icon = R.drawable.ic_link,
        )

        return HomeScreenState.Data(
            headerState = HeaderState(
                text = "Χημεία Α' Λυκείου",
                helpText = "Καλώς ήρθες πίσω!", // να διορθωθεί ανάλογα με το seridays
                seriDays = "5 μέρες" //να διορθωθεί, θα το παίρνουμε από το json
            ),
            gamePanelState = GamePanelState(
                gameTabs = listOf(quiz, timeAttack, periodic, balance, match),
                text = "Παιχνίδια",
                helpText = "5 τρόποι εξάσκησης"
            ),
            reviewBannerState = provideReviewBannerState(reviewQuestionsCount)
        )
    }

    private fun provideReviewBannerState(reviewQuestionsCount: Int): ReviewBannerState? {
        if (reviewQuestionsCount <= 0) return null

        val title = if (reviewQuestionsCount == 1) {
            "1 ερώτηση περιμένει"
        } else {
            "$reviewQuestionsCount ερωτήσεις περιμένουν"
        }

        return ReviewBannerState(
            title = title,
            description = "Επανάληψη λαθών"
        )
    }
}