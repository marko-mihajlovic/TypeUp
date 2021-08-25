package com.typeup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.typeup.R
import com.typeup.model.AppInfo
import javax.inject.Inject

/**
 * @author Marko Mihajlovic - Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
class ListOfAppsAdapter @Inject constructor(
    val context: Context,
    val inflater : LayoutInflater,
    var appInfoList : List<AppInfo> = listOf()
) : BaseAdapter() {


    fun updateAdapter(list : List<AppInfo>){
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
            v = inflater.inflate(R.layout.row_app_info, parent, false)


        v?.findViewById<TextView>(R.id.nameItem)?.text = getItem(position).appName

        return v
    }




}