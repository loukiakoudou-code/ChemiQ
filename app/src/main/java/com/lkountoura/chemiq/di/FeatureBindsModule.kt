package com.lkountoura.chemiq.di


import com.lkountoura.chemiq.data.repository.question.QuestionLocalDataSource
import com.lkountoura.chemiq.data.repository.question.QuestionLocalDataSourceImpl
import com.lkountoura.chemiq.data.repository.question.QuestionRemoteDataSource
import com.lkountoura.chemiq.data.repository.question.QuestionRemoteDataSourceImpl
import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import com.lkountoura.chemiq.data.repository.question.QuestionRepositoryImpl
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsLocalDataSource
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsLocalDataSourceImpl
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRemoteDataSource
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRemoteDataSourceImpl
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRepository
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRepositoryImpl
import com.lkountoura.chemiq.data.repository.result.ResultLocalDataSource
import com.lkountoura.chemiq.data.repository.result.ResultLocalDataSourceImpl
import com.lkountoura.chemiq.data.repository.result.ResultRepository
import com.lkountoura.chemiq.data.repository.result.ResultRepositoryImpl
import com.lkountoura.chemiq.usecase.questionsUseCase.FetchQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveQuestionsUseCaseImpl
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveReviewQuestionsCountUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveReviewQuestionsCountUseCaseImpl
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreQuestionsUseCaseImpl
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreResultsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreResultsUseCaseImpl
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreWrongAnswerUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreWrongAnswerUseCaseImpl
import com.lkountoura.chemiq.usecase.quizSelectionsUseCase.FetchUnitsUseCase
import com.lkountoura.chemiq.usecase.quizSelectionsUseCase.ObserveUnitsUseCase
import com.lkountoura.chemiq.usecase.quizSelectionsUseCase.ObserveUnitsUseCaseImpl
import com.lkountoura.chemiq.usecase.quizSelectionsUseCase.StoreUnitsUseCaseImpl
import com.lkountoura.chemiq.usecase.resultUseCase.ObserveResultUseCase
import com.lkountoura.chemiq.usecase.resultUseCase.ObserveResultUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FeatureBindsModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFetchUnitsUseCase(
        fetchUnitsUseCaseImpl: StoreUnitsUseCaseImpl
    ): FetchUnitsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindObserveResultUseCase(
        observeResultUseCaseImpl: ObserveResultUseCaseImpl
    ): ObserveResultUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindStoreResultsUseCase(
        storeResultsUseCaseImpl: StoreResultsUseCaseImpl
    ): StoreResultsUseCase

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
    abstract fun bindResultRepository(
        resultRepositoryImpl: ResultRepositoryImpl
    ): ResultRepository


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

    @Binds
    @ViewModelScoped
    abstract fun bindObserveResultLocalDataSource(
        resultLocalDataSourceImpl: ResultLocalDataSourceImpl
    ): ResultLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindFetchQuestionsUseCase(
        fetchQuestionsUseCaseImpl: StoreQuestionsUseCaseImpl
    ): FetchQuestionsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindObserveQuestionsUseCase(
        observeQuestionsUseCaseImpl: ObserveQuestionsUseCaseImpl
    ): ObserveQuestionsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindQuestionRepository(
        questionRepositoryImpl: QuestionRepositoryImpl
    ): QuestionRepository

    @Binds
    @ViewModelScoped
    abstract fun bindQuestionRemoteDataSource(
        questionRemoteDataSourceImpl: QuestionRemoteDataSourceImpl
    ): QuestionRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindQuestionLocalDataSource(
        questionLocalDataSourceImpl: QuestionLocalDataSourceImpl
    ): QuestionLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindStoreWrongAnswerUseCase(
        storeWrongAnswerUseCaseImpl: StoreWrongAnswerUseCaseImpl
    ): StoreWrongAnswerUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindObserveReviewQuestionsCountUseCase(
        observeReviewQuestionsCountUseCaseImpl: ObserveReviewQuestionsCountUseCaseImpl
    ): ObserveReviewQuestionsCountUseCase

}
