@file:Suppress("unused")

package com.typeup.options

interface CompanionEnumWithText<T : Enum<T>>

interface EnumWithText {
    val text: String
}

inline fun <reified T> CompanionEnumWithText<T>.getItemWithText(
    value: String
): T where T : Enum<T>, T : EnumWithText {
    return enumValues<T>().first { it.text == value }
}

inline fun <reified T> CompanionEnumWithText<T>.getTexts(): Array<String> where T : Enum<T>, T : EnumWithText {
    return enumValues<T>().map { it.text }.toTypedArray()
}