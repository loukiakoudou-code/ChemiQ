package com.lkountoura.chemiq.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FeatureBindsModule {
//
//    @Binds
//    @ViewModelScoped
//    abstract fun bindHomeScreenProvider(): HomeScreenProvider
}