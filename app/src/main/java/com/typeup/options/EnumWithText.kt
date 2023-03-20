package com.typeup.options

interface EnumCompanion<T : Enum<T>>

interface EnumWithText {
    val text: String
}

@Suppress("unused")
inline fun <reified T> EnumCompanion<T>.getItemWithText(
    value: String
): T where T : Enum<T>, T : EnumWithText {
    return enumValues<T>().first { it.text == value }
}