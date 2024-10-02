package com.aaron.advancebookings.di

import android.content.Context
import com.aaron.advancebookings.BuildConfig
import com.aaron.advancebookings.data.DataRepository
import com.aaron.advancebookings.data.MainDataRepository
import com.aaron.advancebookings.data.local.MainPreferenceDataStore
import com.aaron.advancebookings.data.local.PreferenceDataStoreAPI
import com.aaron.advancebookings.data.remote.MainResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun getMainPreferenceDataStore(
        @ApplicationContext context: Context,
    ): PreferenceDataStoreAPI {
        return MainPreferenceDataStore(context)
    }

    @Singleton
    @Provides
    fun provideBasicOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient
            .Builder()

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideTokenLevelRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(MainResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeeDirectoryDataRepository(
        retrofit: Retrofit,
        dataStore:PreferenceDataStoreAPI
    ): DataRepository =
        MainDataRepository(
            retrofit,
            dataStore
        )
}