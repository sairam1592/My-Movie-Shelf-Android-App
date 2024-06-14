package com.example.emergetestapplication.emerge.di

import android.content.Context
import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSource
import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSourceImpl
import com.example.emergetestapplication.emerge.data.helper.SharedPreferencesHelper
import com.example.emergetestapplication.emerge.data.repository.AuthRepository
import com.example.emergetestapplication.emerge.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(
        @ApplicationContext context: Context,
    ): SharedPreferencesHelper = SharedPreferencesHelper(context)

    @Provides
    @Singleton
    fun provideLocalDataSource(sharedPreferencesHelper: SharedPreferencesHelper): AuthLocalDataSource =
        AuthLocalDataSourceImpl(sharedPreferencesHelper)

    @Provides
    @Singleton
    fun provideAuthRepository(localDataSource: AuthLocalDataSource): AuthRepository = AuthRepositoryImpl(localDataSource)
}
