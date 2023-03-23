package com.typeup.di

import com.typeup.search_apps.data.data_source.InstalledAppsDataSource
import com.typeup.search_apps.data.data_source.InstalledAppsDataSourceImpl
import com.typeup.search_apps.data.repo.InstalledAppsRepo
import com.typeup.search_apps.data.repo.InstalledAppsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchAppsModule {

    @Provides
    fun provideRepo(
        dataSource: InstalledAppsDataSource
    ): InstalledAppsRepo {
        return InstalledAppsRepoImpl(dataSource)
    }

    @Provides
    fun provideDataSource(): InstalledAppsDataSource {
        return InstalledAppsDataSourceImpl()
    }

}