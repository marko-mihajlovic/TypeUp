package com.typeup.di

import android.content.Context
import com.typeup.home.data_source.InstalledAppsDataSource
import com.typeup.home.data_source.InstalledAppsDataSourceImpl
import com.typeup.home.repo.InstalledAppsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideInstalledAppsRepoImpl(
        @ApplicationContext context: Context,
        dataSource: InstalledAppsDataSource
    ): InstalledAppsRepoImpl {
        return InstalledAppsRepoImpl(context, dataSource)
    }

    @Provides
    fun provideInstalledAppsDataSourceImpl(
        @ApplicationContext context: Context
    ): InstalledAppsDataSourceImpl {
        return InstalledAppsDataSourceImpl(context)
    }

}