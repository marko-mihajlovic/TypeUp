@file:Suppress("unused")

package com.typeup.options

interface CompanionEnumWithId<T : Enum<T>>

interface EnumWithId {
    val id: Int
}

inline fun <reified T> CompanionEnumWithId<T>.getItemWithId(
    value: Int
): T where T : Enum<T>, T : EnumWithId {
    return enumValues<T>().first { it.id == value }
}

inline fun <reified T> CompanionEnumWithId<T>.getIds(): Array<Int> where T : Enum<T>, T : EnumWithId {
    return enumValues<T>().map { it.id }.toTypedArray()
}