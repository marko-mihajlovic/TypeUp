package com.typeup.search_apps.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.typeup.databinding.ItemAppRowBinding
import com.typeup.search_apps.data.model.AppInfo

class AppsListViewAdapter(
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

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val (v, viewHolder) = getViewAndViewHolder(recycledView, parent)

        viewHolder.textView.text = getItem(position).appName

        return v
    }

    private fun getViewAndViewHolder(
        recycledView: View?,
        parent: ViewGroup
    ): Pair<View, ViewHolder> {
        var v = recycledView

        val viewHolder: ViewHolder
        if (v == null) {
            val binding = ItemAppRowBinding.inflate(inflater, parent, false)
            v = binding.root

            viewHolder = ViewHolder(binding)
            v.setTag(viewHolder)
        } else {
            viewHolder = v.tag as ViewHolder
        }

        return Pair(v, viewHolder)
    }

    private class ViewHolder(binding: ItemAppRowBinding) {
        val textView: TextView

        init {
            this.textView = binding.nameItem
        }
    }
}