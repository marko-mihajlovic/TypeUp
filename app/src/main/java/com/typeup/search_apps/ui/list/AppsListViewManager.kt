package com.typeup.search_apps.ui.list

import android.content.Context
import android.widget.EditText
import android.widget.ListView
import com.typeup.BuildConfig
import com.typeup.search_apps.data.model.AppInfo

object AppsListViewManager {

    fun init(
        context: Context,
        adapter: AppsListViewAdapter,
        listView: ListView,
        editText: EditText
    ) {
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            require(item is AppInfo)
            if (item.appId == BuildConfig.APPLICATION_ID)
                SelectedAppActions.showAppOptions(context, item)
            else
                SelectedAppActions.openApp(context, item, editText)
        }

        listView.setOnItemLongClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            require(item is AppInfo)

            SelectedAppActions.showAppOptions(context, item)

            return@setOnItemLongClickListener (true)
        }
    }

}