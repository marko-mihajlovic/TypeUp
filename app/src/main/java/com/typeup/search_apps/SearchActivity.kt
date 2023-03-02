package com.typeup.search_apps

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.typeup.R
import com.typeup.options.MainOptions
import com.typeup.options.main.ThemeSettings
import com.typeup.options.main.more.PolicyDialog
import com.typeup.search_apps.data.model.AppInfo
import com.typeup.search_apps.data.model.SearchAppsUiState
import com.typeup.search_apps.list.AppsListViewAdapter
import com.typeup.search_apps.list.SelectedAppActions
import com.typeup.util.KeyboardUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchAppsViewModel by viewModels()

    @Inject
    lateinit var appsListViewAdapter: AppsListViewAdapter

    private var searchInput: EditText? = null
    private var msgTxt: TextView? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInput = findViewById(R.id.searchInput)
        msgTxt = findViewById(R.id.msgTxt)
        KeyboardUtil.show(this, searchInput)
    }

    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { x ->
                    runAfterLoadingOnce()

                    when (x) {
                        is SearchAppsUiState.Error -> {
                            updateMsgTxt(true, x.msg)
                            appsListViewAdapter.updateAdapter(emptyList())
                        }
                        is SearchAppsUiState.Loading -> {
                            updateMsgTxt(true, x.msg)
                            appsListViewAdapter.updateAdapter(emptyList())
                        }
                        is SearchAppsUiState.Success -> {
                            updateMsgTxt(false)
                            appsListViewAdapter.updateAdapter(x.list)
                        }
                    }
                }
            }
        }
    }

    private fun updateMsgTxt(visible: Boolean, msg: String = "") {
        msgTxt?.visibility = if (visible) View.VISIBLE else View.GONE
        msgTxt?.text = if (visible) msg else ""
    }

    private var isFirstTime = true
    private fun runAfterLoadingOnce() {
        if (!isFirstTime) return
        isFirstTime = false

        ThemeSettings.applyExistingTheme(this)
        PolicyDialog.tryToShow(this, false)

        confListViewAndAdapter()
        confRefreshLayout()

        searchInput?.doAfterTextChanged { text: Editable? ->
            viewModel.searchApps(text?.toString()?.trim()?.lowercase() ?: "")
        }

        findViewById<View>(R.id.optionsBtn)?.setOnClickListener {
            MainOptions.showDialog(this, ::refreshSearch)
        }
    }

    private fun confListViewAndAdapter() {
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = appsListViewAdapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            searchInput?.text?.clear()

            val element = parent.getItemAtPosition(position) as AppInfo
            SelectedAppActions.openApp(this, element)
        }

        listView.setOnItemLongClickListener { parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            SelectedAppActions.showAppOptions(this, element)

            return@setOnItemLongClickListener (true)
        }
    }

    private fun confRefreshLayout() {
        refreshLayout = findViewById(R.id.refreshLayout)
        refreshLayout?.setOnRefreshListener {
            refreshSearch()
            refreshLayout?.isRefreshing = false
        }
    }

    private fun refreshSearch() {
        val text = searchInput?.text?.toString()?.trim()?.lowercase() ?: ""
        viewModel.searchApps(text, true)
    }
}