package com.typeup.options.main

import android.content.Context
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.util.AppUtil
import com.typeup.util.SharedPref

object MaxShownItems {

    private const val maxItemsKey = "maxSizeOfItems"
    private const val min = 1
    private const val max = 5
    public const val default = 3

    fun showDialog(context: Context) {
        val dialogView = AppUtil.getInflater(context).inflate(R.layout.number_picker_dialog, null)
        val numPicker = dialogView.findViewById<NumberPicker>(R.id.dialog_number_picker)
        numPicker.maxValue = max
        numPicker.minValue = min
        numPicker.wrapSelectorWheel = false

        numPicker.value = getMaxItems(context)

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.numPickerTitle))
            .setMessage(context.getString(R.string.numPickerLongTxt))
            .setView(dialogView)
            .setPositiveButton(context.getString(R.string.setTxt)) { dialog, _ ->
                setMaxItems(context, numPicker.value)
                dialog.cancel()
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setMaxItems(context: Context, i: Int) {
        SharedPref.edit(context).putInt(maxItemsKey, i).apply()
    }

    fun getMaxItems(context: Context): Int {
        return SharedPref.get(context).getInt(maxItemsKey, default)
    }

}