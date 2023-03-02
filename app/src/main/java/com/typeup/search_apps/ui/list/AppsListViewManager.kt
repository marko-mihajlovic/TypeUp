package com.typeup.search_apps.ui.list

import android.content.Context
import android.widget.EditText
import android.widget.ListView
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
            editText.text?.clear()

            val item = parent.getItemAtPosition(position)
            if (item is AppInfo)
                SelectedAppActions.openApp(context, item)
        }

        listView.setOnItemLongClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            if (item is AppInfo)
                SelectedAppActions.showAppOptions(context, item)

            return@setOnItemLongClickListener (true)
        }
    }

}