package com.typeup.di

import android.content.Context
import com.typeup.search_apps.list.AppsListViewAdapter
import com.typeup.util.AppUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideAppsListViewAdapter(
        @ActivityContext context: Context
    ): AppsListViewAdapter {
        return AppsListViewAdapter(AppUtil.getInflater(context))
    }

}