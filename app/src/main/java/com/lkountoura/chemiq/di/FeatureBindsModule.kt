package com.lkountoura.chemiq.di


import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsLocalDataSource
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsLocalDataSourceImpl
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRemoteDataSource
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRemoteDataSourceImpl
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRepository
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRepositoryImpl
import com.lkountoura.chemiq.usecase.FetchUnitsUseCase
import com.lkountoura.chemiq.usecase.ObserveUnitsUseCase
import com.lkountoura.chemiq.usecase.ObserveUnitsUseCaseImpl
import com.lkountoura.chemiq.usecase.StoreUnitsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FeatureBindsModule {
//
//    @Binds
//    @ViewModelScoped
//    abstract fun bindHomeScreenProvider(): HomeScreenProvider

    @Binds
    @ViewModelScoped
    abstract fun bindFetchUnitsUseCase(
        fetchUnitsUseCaseImpl: StoreUnitsUseCaseImpl
    ): FetchUnitsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindObserveUnitsUseCase(
        observeUnitsUseCaseImpl: ObserveUnitsUseCaseImpl
    ): ObserveUnitsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindQuizSelectionsRepository(
        quizSelectionsRepositoryImpl: QuizSelectionsRepositoryImpl
    ): QuizSelectionsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindQuizSelectionsRemoteDataSource(
        quizSelectionsRemoteDataSourceImpl: QuizSelectionsRemoteDataSourceImpl
    ): QuizSelectionsRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindQuizSelectionsLocalDataSource(
        quizSelectionsLocalDataSourceImpl: QuizSelectionsLocalDataSourceImpl
    ): QuizSelectionsLocalDataSource
}
