package com.typeup.options.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.typeup.R
import com.typeup.options.CompanionEnumWithText
import com.typeup.options.EnumWithText
import com.typeup.options.getItemWithText
import com.typeup.options.getTexts
import com.typeup.util.SharedPref

object ThemeSettings {

    private const val themeKey = "selectedTheme"

    private enum class Theme(override val text: String) : EnumWithText {
        LIGHT("Light"),
        DARK("Dark"),
        SYSTEM("System Default");

        companion object : CompanionEnumWithText<Theme> {
            fun valueOfWithDefault(theme: String, default: Theme): Theme {
                try {
                    return Theme.valueOf(theme)
                } catch (e: IllegalArgumentException) {
                    return default
                }
            }
        }
    }

    fun showDialog(context: Context) {
        val list = Theme.getTexts()
        var selectedTheme = getSavedTheme()
        var selectedPos = list.indexOf(selectedTheme.text)

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.chooseThemeTitle)
            .setSingleChoiceItems(list, selectedPos) { _, pos ->
                selectedTheme = Theme.getItemWithText(list[pos])
                selectedPos = pos
            }
            .setPositiveButton(R.string.saveTxt) { dialog, _ ->
                dialog.cancel()

                rememberTheme(selectedTheme)
                changeDeviceTheme(selectedTheme)
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    fun applySavedTheme() {
        changeDeviceTheme(getSavedTheme())
    }

    private fun changeDeviceTheme(theme: Theme) {
        when (theme) {
            Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun rememberTheme(theme: Theme) {
        SharedPref.edit().putString(themeKey, theme.toString()).apply()
    }

    private fun getSavedTheme(): Theme {
        return Theme.valueOfWithDefault(getSavedThemeString(), Theme.SYSTEM)
    }

    private fun getSavedThemeString(): String {
        return SharedPref.get().getString(themeKey, Theme.SYSTEM.toString())
            ?: Theme.SYSTEM.toString()
    }

}