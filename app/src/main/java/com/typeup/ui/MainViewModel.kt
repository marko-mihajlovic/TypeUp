package com.typeup.ui

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.typeup.model.AppInfo
import com.typeup.ui.options.MaxShownItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Marko Mihajlovic - Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
) : ViewModel(){

    private val pm : PackageManager? = application.packageManager

    val appListFiltered : MutableLiveData<MutableList<AppInfo>> by lazy {
        MutableLiveData<MutableList<AppInfo>>().also {
            loadAppListAsync(it)
        }
    }

    private val ioScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    private fun loadAppListAsync(it : MutableLiveData<MutableList<AppInfo>>){
        ioScope.launch {
            loadAppList(it)
        }
    }

    private val appFullList : MutableList<AppInfo> = mutableListOf()
    private fun loadAppList(it : MutableLiveData<MutableList<AppInfo>>){
        val main = Intent(Intent.ACTION_MAIN, null)
        main.addCategory(Intent.CATEGORY_LAUNCHER)

        appFullList.clear()

        val infoList : List<ResolveInfo> = pm?.queryIntentActivities(main, 0) ?: return
        for (info in infoList)
            appFullList.add(AppInfo(
                    info.activityInfo.applicationInfo.packageName,
                    info.activityInfo.name,
                    info.loadLabel(pm).toString()
                ))

        it.postValue(mutableListOf())
    }


    var maxSize = MaxShownItems.getMaxItems(application)
    private var filter = ""

    fun reFilterList(){
        filterList(filter)
    }

    fun filterList(filter : String){
        this.filter = filter
        val newFilteredList : MutableList<AppInfo> = mutableListOf()

        if(filter.isNotBlank()) {
            for (appInfo in appFullList) {
                if (appInfo.appNameLowercase.startsWith(filter))
                    newFilteredList.add(0, appInfo)
                else if (appInfo.appNameLowercase.contains(filter))
                    newFilteredList.add(appInfo)
            }
        }

        if(newFilteredList.size>maxSize)
            appListFiltered.postValue(newFilteredList.subList(0, maxSize))
        else
            appListFiltered.postValue(newFilteredList)
    }


}