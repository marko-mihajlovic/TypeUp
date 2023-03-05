package com.typeup.search_apps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.typeup.databinding.ActivitySearchBinding
import com.typeup.search_apps.ui.SearchAppsInitUiManager
import com.typeup.search_apps.ui.SearchAppsOnUpdateUiManager
import com.typeup.search_apps.ui.list.AppsListViewAdapter
import com.typeup.util.KeyboardUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchAppsViewModel by viewModels()
    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var appsListViewAdapter: AppsListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KeyboardUtil.show(this, binding.searchInput)
    }

    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { x ->
                    runAfterLoadingOnce()

                    SearchAppsOnUpdateUiManager.onUpdate(
                        state = x,
                        binding = binding,
                        adapter = appsListViewAdapter
                    )
                }
            }
        }
    }


    private var isFirstTime = true
    private fun runAfterLoadingOnce() {
        if (isFirstTime) {
            isFirstTime = false

            SearchAppsInitUiManager.init(
                context = this,
                binding = binding,
                viewModel = viewModel,
                adapter = appsListViewAdapter,
            )
        }
    }

}