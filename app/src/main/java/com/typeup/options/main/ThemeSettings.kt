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

        companion object : CompanionEnumWithText<Theme>
    }


    fun showDialog(context: Context) {
        val list = Theme.getTexts()
        var selectedTheme = getSavedTheme(context)
        var selectedPos = list.indexOf(selectedTheme.text)

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.chooseThemeTitle)
            .setSingleChoiceItems(list, selectedPos) { _, pos ->
                selectedTheme = Theme.getItemWithText(list[pos])
                selectedPos = pos
            }
            .setPositiveButton(R.string.saveTxt) { dialog, _ ->
                dialog.cancel()
                setNewTheme(context, selectedTheme)
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setNewTheme(context: Context, theme: Theme) {
        when (theme) {
            Theme.LIGHT -> {
                rememberTheme(context, Theme.LIGHT)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Theme.DARK -> {
                rememberTheme(context, Theme.DARK)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Theme.SYSTEM -> {
                rememberTheme(context, Theme.SYSTEM)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
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