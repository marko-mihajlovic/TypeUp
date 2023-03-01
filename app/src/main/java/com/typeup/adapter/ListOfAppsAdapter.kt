package com.typeup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.typeup.R
import com.typeup.home.model.AppInfo

class ListOfAppsAdapter(
    private val inflater: LayoutInflater,
    private var appInfoList: List<AppInfo> = listOf()
) : BaseAdapter() {


    fun updateAdapter(list: List<AppInfo>) {
        appInfoList = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return appInfoList.size
    }

    override fun getItem(position: Int): AppInfo {
        return appInfoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
        var v = view

        if (v == null)
            v = inflater.inflate(R.layout.item_app_row, parent, false)


        v?.findViewById<TextView>(R.id.nameItem)?.text = getItem(position).appName

        return v
    }


}