@file:Suppress("unused")

package com.typeup.options

interface CompanionEnumWithBool<T : Enum<T>>

interface EnumWithBool {
    val bool: Boolean
}

inline fun <reified T> CompanionEnumWithBool<T>.getBools(): BooleanArray where T : Enum<T>, T : EnumWithBool {
    return enumValues<T>().getBools()
}

inline fun <reified T> Array<T>.getBools(): BooleanArray where T : Enum<T>, T : EnumWithBool {
    return this.map { it.bool }.toBooleanArray()
}