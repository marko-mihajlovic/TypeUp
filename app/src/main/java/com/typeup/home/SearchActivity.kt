package com.typeup.home

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.typeup.R
import com.typeup.adapter.ListOfAppsAdapter
import com.typeup.home.model.AppInfo
import com.typeup.home.model.SearchAppsUiState
import com.typeup.options.main.MainOptions
import com.typeup.options.common.PolicyDialog
import com.typeup.options.common.SelectedAppActions
import com.typeup.options.main.ThemeSettings
import com.typeup.util.KeyboardUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchAppsViewModel by viewModels()

    @Inject
    lateinit var listOfAppsAdapter: ListOfAppsAdapter

    private var searchInput: EditText? = null
    private var msgTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
                            listOfAppsAdapter.updateAdapter(emptyList())
                        }
                        is SearchAppsUiState.Loading -> {
                            updateMsgTxt(true, x.msg)
                            listOfAppsAdapter.updateAdapter(emptyList())
                        }
                        is SearchAppsUiState.Success -> {
                            updateMsgTxt(false)
                            listOfAppsAdapter.updateAdapter(x.list)
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

        searchInput?.doAfterTextChanged { text: Editable? ->
            viewModel.searchApps(text?.toString()?.trim()?.lowercase() ?: "")
        }

        findViewById<ImageView>(R.id.optionsBtn)?.setOnClickListener {
            MainOptions.showDialog(this)
        }
    }

    private fun confListViewAndAdapter() {
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = listOfAppsAdapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            SelectedAppActions.openApp(this, element)
        }

        listView.setOnItemLongClickListener { parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            SelectedAppActions.showAppOptions(this, element)

            return@setOnItemLongClickListener (true)
        }
    }

}