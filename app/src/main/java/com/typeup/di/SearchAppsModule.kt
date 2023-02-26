package com.typeup.di

import com.typeup.home.data_source.InstalledAppsDataSource
import com.typeup.home.data_source.InstalledAppsDataSourceImpl
import com.typeup.home.repo.InstalledAppsRepo
import com.typeup.home.repo.InstalledAppsRepoImpl
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