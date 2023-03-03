package com.typeup.di

import android.content.Context
import com.typeup.search_apps.data.data_source.InstalledAppsDataSource
import com.typeup.search_apps.data.data_source.InstalledAppsDataSourceImpl
import com.typeup.search_apps.data.repo.InstalledAppsRepo
import com.typeup.search_apps.data.repo.InstalledAppsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchAppsModule {

    @Provides
    fun provideRepo(
        @ApplicationContext context: Context,
        dataSource: InstalledAppsDataSource
    ): InstalledAppsRepo {
        return InstalledAppsRepoImpl(context, dataSource)
    }

    @Provides
    fun provideDataSource(
        @ApplicationContext context: Context
    ): InstalledAppsDataSource {
        return InstalledAppsDataSourceImpl(context)
    }

}