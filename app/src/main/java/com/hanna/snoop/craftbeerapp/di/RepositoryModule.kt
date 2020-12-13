package com.hanna.snoop.craftbeerapp.di

import com.hanna.snoop.craftbeerapp.repository.BeerRepository
import com.hanna.snoop.craftbeerapp.repository.BeerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun beerRepository(repository: BeerRepositoryImpl): BeerRepository
}