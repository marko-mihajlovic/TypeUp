package com.typeup.search_apps.ui

import android.content.Context
import android.text.Editable
import androidx.core.widget.doAfterTextChanged
import com.typeup.databinding.ActivitySearchBinding
import com.typeup.options.MainOptions
import com.typeup.options.main.ThemeSettings
import com.typeup.options.main.more.PolicyDialog
import com.typeup.search_apps.SearchAppsViewModel
import com.typeup.search_apps.ui.list.AppsListViewAdapter
import com.typeup.search_apps.ui.list.AppsListViewManager

object SearchAppsInitUiManager {

    fun init(
        context: Context,
        binding: ActivitySearchBinding,
        viewModel: SearchAppsViewModel,
        adapter: AppsListViewAdapter,
    ) {

        ThemeSettings.applySavedTheme()
        PolicyDialog.tryToShow(context, false)

        AppsListViewManager.init(
            context = context,
            adapter = adapter,
            listView = binding.listView,
            editText = binding.searchInput
        )

        initRefreshLayout(binding, viewModel)

        binding.searchInput.doAfterTextChanged { text: Editable? ->
            viewModel.searchApps(text?.toString()?.trim()?.lowercase() ?: "")
        }

        binding.optionsBtn.setOnClickListener {
            MainOptions.showDialog(context)
        }

    }

    private fun initRefreshLayout(
        binding: ActivitySearchBinding,
        viewModel: SearchAppsViewModel,
    ) {
        binding.refreshLayout.setOnRefreshListener {
            refreshSearch(binding, viewModel)
            binding.refreshLayout.isRefreshing = false
        }
    }

    fun refreshSearch(
        binding: ActivitySearchBinding,
        viewModel: SearchAppsViewModel,
        refresh: Boolean = true,
    ) {
        val text = binding.searchInput.text?.toString()?.trim()?.lowercase() ?: ""
        viewModel.searchApps(text, refresh)
    }

}