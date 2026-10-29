package com.lkountoura.chemiq.provider

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class HomeScreenProviderTest {

    private val provider = HomeScreenProvider()

    @Test
    fun noQuestionsToReview_hidesTheBanner() {
        assertNull(provider.provideData(reviewQuestionsCount = 0).reviewBannerState)
    }

    @Test
    fun oneQuestionToReview_usesTheSingular() {
        val bannerState = provider.provideData(reviewQuestionsCount = 1).reviewBannerState

        assertEquals("1 ερώτηση περιμένει", bannerState?.title)
    }

    @Test
    fun manyQuestionsToReview_usesThePlural() {
        val bannerState = provider.provideData(reviewQuestionsCount = 12).reviewBannerState

        assertEquals("12 ερωτήσεις περιμένουν", bannerState?.title)
        assertEquals("Επανάληψη λαθών", bannerState?.description)
    }
}
