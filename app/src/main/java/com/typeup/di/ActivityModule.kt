package com.typeup.di

import android.content.Context
import com.typeup.adapter.ListOfAppsAdapter
import com.typeup.ui.options.SelectedAppActions
import com.typeup.util.getInflater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

/**
 * @author Marko Mihajlovic - Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideListOfAppsAdapter(
        @ActivityContext context: Context
    ): ListOfAppsAdapter {
        return ListOfAppsAdapter(context, getInflater(context))
    }

    @Provides
    fun provideSelectedAppActions(
        @ActivityContext context: Context
    ): SelectedAppActions {
        return SelectedAppActions(context)
    }


}