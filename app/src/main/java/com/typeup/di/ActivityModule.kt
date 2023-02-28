package com.typeup.di

import android.content.Context
import com.typeup.adapter.ListOfAppsAdapter
import com.typeup.options.common.SelectedAppActions
import com.typeup.util.getInflater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideListOfAppsAdapter(
        @ActivityContext context: Context
    ): ListOfAppsAdapter {
        return ListOfAppsAdapter(getInflater(context))
    }

    @Provides
    fun provideSelectedAppActions(
        @ActivityContext context: Context
    ): SelectedAppActions {
        return SelectedAppActions(context)
    }

}