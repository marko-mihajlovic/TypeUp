package com.typeup.di

import com.typeup.search_apps.data.data_source.InstalledAppsDataSource
import com.typeup.search_apps.data.data_source.InstalledAppsDataSourceImpl
import com.typeup.search_apps.data.repo.InstalledAppsRepo
import com.typeup.search_apps.data.repo.InstalledAppsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SearchAppsModule {

    @Binds
    fun bindInstalledAppsRepo(
        installedAppsRepoImpl: InstalledAppsRepoImpl
    ): InstalledAppsRepo

    @Binds
    fun bindInstalledAppsDataSource(
        installedAppsDataSourceImpl: InstalledAppsDataSourceImpl
    ): InstalledAppsDataSource

}