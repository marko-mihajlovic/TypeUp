package com.typeup.search_apps.ui

import android.content.Context
import android.text.Editable
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

        ThemeSettings.applyExistingTheme(context)
        PolicyDialog.tryToShow(context, false)

        AppsListViewManager.init(
            context = context,
            adapter = adapter,
            listView = binding.listView,
            editText = binding.searchInput
        )

        initRefreshLayout(
            binding.refreshLayout,
            editText = binding.searchInput,
            viewModel = viewModel
        )

        binding.searchInput.doAfterTextChanged { text: Editable? ->
            viewModel.searchApps(text?.toString()?.trim()?.lowercase() ?: "")
        }

        binding.optionsBtn.setOnClickListener {
            MainOptions.showDialog(context) {
                refreshSearch(binding.searchInput, viewModel)
            }
        }

    }

    private fun initRefreshLayout(
        swipeRefreshLayout: SwipeRefreshLayout,
        editText: EditText,
        viewModel: SearchAppsViewModel,
    ) {
        swipeRefreshLayout.setOnRefreshListener {
            refreshSearch(editText, viewModel)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshSearch(
        editText: EditText,
        viewModel: SearchAppsViewModel,
    ) {
        val text = editText.text?.toString()?.trim()?.lowercase() ?: ""
        viewModel.searchApps(text, true)
    }

}