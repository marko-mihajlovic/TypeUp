package com.typeup.options.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.databinding.LayoutNumberPickerBinding
import com.typeup.search_apps.ui.OnRefreshEvent
import com.typeup.util.AppUtil
import com.typeup.util.SharedPref

object MaxShownItems {

    private const val maxItemsKey = "maxSizeOfItems"
    const val min = 1
    const val max = 5
    const val default = 3

    fun showDialog(context: Context) {
        val binding = LayoutNumberPickerBinding.inflate(AppUtil.getInflater(context))
        val numPicker = binding.dialogNumberPicker

        numPicker.maxValue = max
        numPicker.minValue = min
        numPicker.wrapSelectorWheel = false

        numPicker.value = getMaxItems()

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.numPickerTxt)
            .setView(binding.root)
            .setPositiveButton(R.string.saveTxt) { dialog, _ ->
                dialog.cancel()

                setMaxItems(numPicker.value)

                if (context is OnRefreshEvent)
                    context.onRefresh(false)
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setMaxItems(i: Int) {
        SharedPref.edit().putInt(maxItemsKey, i).commit()
    }

    fun getMaxItems(): Int {
        return SharedPref.get().getInt(maxItemsKey, default)
    }

}