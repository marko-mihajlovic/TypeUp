package com.typeup.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.typeup.R
import com.typeup.adapter.ListOfAppsAdapter
import com.typeup.model.AppInfo
import com.typeup.ui.options.MainOptions
import com.typeup.ui.options.PolicyDialog
import com.typeup.ui.options.SelectedAppActions
import com.typeup.ui.options.ThemeSettings
import com.typeup.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * @author Marko Mihajlovic aka Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()

    @Inject lateinit var listOfAppsAdapter : ListOfAppsAdapter
    @Inject lateinit var selectedAppActions : SelectedAppActions

    private var searchInput : EditText? = null
    private var msgTxt : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeSettings.applyExistingTheme(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        searchInput = findViewById(R.id.searchInput)


        viewModel.appListFiltered.observe(this, { list ->
            updateUI(list)
        })
    }

    var firstTime : Boolean = true
    private fun updateUI(list : List<AppInfo>){
        if(firstTime){
            firstTime = false
            runOnceAfterListIsLoaded()
        }

        listOfAppsAdapter.updateAdapter(list)
        updateMsgTxt(list.isEmpty())
    }


    private fun runOnceAfterListIsLoaded(){
        msgTxt = findViewById(R.id.msgTxt)

        confListViewAndAdapter()
        onInputChange()
        PolicyDialog.tryToShow(this, false)
        findViewById<ImageView>(R.id.optionsImg).setOnClickListener { MainOptions.showDialog(this, viewModel) }
    }

    private fun updateMsgTxt(isListEmpty: Boolean){
        val visibleMsg : Boolean = isListEmpty && getInputText().isNotBlank()

        msgTxt?.visibility = if(visibleMsg) VISIBLE else GONE
        msgTxt?.text = if(visibleMsg) getString(R.string.noResultsTxt) else ""
    }


    private fun confListViewAndAdapter(){
        val listView : ListView = findViewById(R.id.listView)
        listView.adapter = listOfAppsAdapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            selectedAppActions.openSelectedApp(element)
        }

        listView.setOnItemLongClickListener{ parent, _, position, _ ->
            val element = parent.getItemAtPosition(position) as AppInfo
            selectedAppActions.showLongClickOptions(element)

            return@setOnItemLongClickListener(true)
        }
    }



    private fun onInputChange(){
        viewModel.filterList(getInputText())
        searchInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                viewModel.filterList(editable.toString().trim().lowercase())
            }
        })
    }

    private fun getInputText(): String {
        return searchInput?.text.toString().trim().lowercase()
    }

    override fun onResume() {
        super.onResume()

        toggleKeyboard(this, searchInput, true)
    }

    override fun onPause() {
        super.onPause()

        toggleKeyboard(this, searchInput,false)
    }

}