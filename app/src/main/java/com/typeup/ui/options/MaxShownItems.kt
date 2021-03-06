package com.typeup.ui.options

import android.content.Context
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.ui.MainViewModel
import com.typeup.util.getInflater
import com.typeup.util.getSharedPref

/**
 * @author Marko Mihajlovic aka Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
object MaxShownItems {

    private const val maxItemsKey = "maxSizeOfItems"
    private const val min = 1
    private const val max = 5
    private const val default = 3

    fun showDialog(context: Context, mainViewModel: MainViewModel) {
        val dialogView = getInflater(context).inflate(R.layout.number_picker_dialog, null)
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
                mainViewModel.maxSize = numPicker.value

                mainViewModel.reFilterList()
                dialog.cancel()
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setMaxItems(context : Context, i : Int){
        getSharedPref(context).edit().putInt(maxItemsKey, i).apply()
    }

    fun getMaxItems(context : Context) : Int{
        return getSharedPref(context).getInt(maxItemsKey, default)
    }

}