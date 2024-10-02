package com.aaron.advancebookings.di

import android.content.Context
import com.aaron.advancebookings.di.appresources.AppResources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun getAppResources(@ApplicationContext context: Context): AppResources {
        return AppResources(context)
    }

}