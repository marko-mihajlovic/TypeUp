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
        adapter.updateAdapter(state.data)
        updateNoPaddingMsgTxt(binding, if (state.isLoading) "Loading..." else state.errorMsg())
    }

    private fun updateNoPaddingMsgTxt(
        binding: ActivitySearchBinding,
        msg: String = "",
    ) {
        binding.msgTxt.visibility = if (msg.isEmpty()) View.INVISIBLE else View.VISIBLE
        binding.msgTxt.text = msg
    }
}