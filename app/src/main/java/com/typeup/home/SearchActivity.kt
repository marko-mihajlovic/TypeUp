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
import com.typeup.options.PolicyDialog
import com.typeup.options.SelectedAppActions
import com.typeup.options.ThemeSettings
import com.typeup.util.showToast
import com.typeup.util.toggleKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var listOfAppsAdapter: ListOfAppsAdapter

    @Inject
    lateinit var selectedAppActions: SelectedAppActions

    private var searchInput: EditText? = null
    private var msgTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        searchInput = findViewById(R.id.searchInput)
        msgTxt = findViewById(R.id.msgTxt)

        initUI()
    }

    private fun initUI() {
        val viewModel: SearchAppsViewModel by viewModels()

        confListViewAndAdapter()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { x ->
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

        searchInput?.doAfterTextChanged { text: Editable? ->
            viewModel.searchApps(
                text?.toString()?.trim()?.lowercase() ?: ""
            )
        }

        findViewById<ImageView>(R.id.optionsBtn)?.setOnClickListener {
            showToast(this, "TODO")
        }
    }

    private fun updateMsgTxt(visible: Boolean, msg: String = "") {
        msgTxt?.visibility = if (visible) View.VISIBLE else View.GONE
        msgTxt?.text = if (visible) msg else ""
    }

    private fun confListViewAndAdapter() {
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = listOfAppsAdapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            selectedAppActions.openSelectedApp(element)
        }

        listView.setOnItemLongClickListener { parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            selectedAppActions.showLongClickOptions(element)

            return@setOnItemLongClickListener (true)
        }
    }


    override fun onResume() {
        super.onResume()

        toggleKeyboard(this, searchInput, true)
    }

    override fun onPause() {
        super.onPause()

        toggleKeyboard(this, searchInput, false)
    }
}