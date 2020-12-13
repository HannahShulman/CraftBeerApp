package com.hanna.snoop.craftbeerapp.di

import android.content.Context
import androidx.room.Room
import com.hanna.snoop.craftbeerapp.datasource.db.AppDb
import com.hanna.snoop.craftbeerapp.datasource.db.CraftBeerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext appContext: Context): AppDb =
        Room.databaseBuilder(appContext, AppDb::class.java, "app-db").build()

    @Provides
    @Singleton
    fun provideDao(db: AppDb): CraftBeerDao = db.craftBeerDao()
}