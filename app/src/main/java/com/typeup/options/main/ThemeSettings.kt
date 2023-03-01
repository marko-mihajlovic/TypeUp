package com.typeup.options.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.typeup.R
import com.typeup.util.SharedPref

object ThemeSettings {

    private const val themeKey = "selectedTheme"

    private enum class Theme(val positionInList: Int) {
        LIGHT(0), DARK(1), SYSTEM(2)
    }


    fun showDialog(context: Context) {
        var selectedItem = getSavedTheme(context).positionInList

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.chooseThemeTitle))
            .setSingleChoiceItems(R.array.theme_settings, selectedItem) { _, pos ->
                selectedItem = pos
            }
            .setPositiveButton(context.getString(R.string.saveTxt)) { dialog, _ ->
                dialog.cancel()
                setNewTheme(context, selectedItem)
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setNewTheme(context: Context, positionInList: Int) {
        when (positionInList) {
            Theme.LIGHT.positionInList -> {
                rememberTheme(context, Theme.LIGHT)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Theme.DARK.positionInList -> {
                rememberTheme(context, Theme.DARK)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Theme.SYSTEM.positionInList -> {
                rememberTheme(context, Theme.SYSTEM)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else -> {}
        }
    }

    fun applyExistingTheme(context: Context) {
        when (getSavedTheme(context)) {
            Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }


    private fun rememberTheme(context: Context, theme: Theme) {
        SharedPref.edit(context).putString(themeKey, theme.toString()).apply()
    }

    private fun getSavedThemeString(context: Context): String {
        var savedString: String? =
            SharedPref.get(context).getString(themeKey, Theme.SYSTEM.toString())
        if (savedString == null)
            savedString = Theme.SYSTEM.toString()

        return savedString
    }

    private fun getSavedTheme(context: Context): Theme {
        return valueOf(getSavedThemeString(context), Theme.SYSTEM)
    }

    private inline fun <reified T : Enum<T>> valueOf(s: String, default: T): T {
        return try {
            java.lang.Enum.valueOf(T::class.java, s)
        } catch (e: IllegalArgumentException) {
            default
        }
    }
}