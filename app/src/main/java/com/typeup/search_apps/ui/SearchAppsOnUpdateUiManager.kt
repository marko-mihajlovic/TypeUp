package com.typeup.search_apps.ui

import android.view.View
import com.typeup.databinding.ActivitySearchBinding
import com.typeup.search_apps.data.model.SearchAppsUiState
import com.typeup.search_apps.ui.list.AppsListViewAdapter

object SearchAppsOnUpdateUiManager {

    fun onUpdate(
        state: SearchAppsUiState,
        binding: ActivitySearchBinding,
        adapter: AppsListViewAdapter,
    ) {

        when (state) {
            is SearchAppsUiState.Error -> {
                updateMsgTxt(binding, true, state.msg)
                adapter.updateAdapter(emptyList())
            }
            is SearchAppsUiState.Loading -> {
                updateMsgTxt(binding, true, state.msg)
                adapter.updateAdapter(emptyList())
            }
            is SearchAppsUiState.Success -> {
                updateMsgTxt(binding, false)
                adapter.updateAdapter(state.list)
            }
        }

    }

    private fun updateMsgTxt(
        binding: ActivitySearchBinding,
        visible: Boolean,
        msg: String = "",
    ) {
        binding.msgTxt.visibility = if (visible) View.VISIBLE else View.GONE
        binding.msgTxt.text = if (visible) msg else ""
    }

}