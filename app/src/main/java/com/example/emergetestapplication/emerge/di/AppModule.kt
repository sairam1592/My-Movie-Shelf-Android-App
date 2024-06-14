package com.example.emergetestapplication.emerge.di

import android.content.Context
import androidx.room.Room
import com.example.emergetestapplication.emerge.AppDatabase
import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSource
import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSourceImpl
import com.example.emergetestapplication.emerge.data.model.db.UserDao
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
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "app_database",
            ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: UserDao): AuthLocalDataSource = AuthLocalDataSourceImpl(userDao)

    @Provides
    @Singleton
    fun provideAuthRepository(localDataSource: AuthLocalDataSource): AuthRepository = AuthRepositoryImpl(localDataSource)
}
