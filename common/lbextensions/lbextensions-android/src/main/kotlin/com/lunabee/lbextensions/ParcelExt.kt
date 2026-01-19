/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")
@file:JvmName("ParcelUtils")

package com.lunabee.lbextensions

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

// Date

/**
 * Write a date value into the parcel at the current dataPosition(),
 * growing dataCapacity() if needed.
 */
fun Parcel.writeDate(value: Date?) {
    writeNullable(value) { writeLong(it.time) }
}

/**
 * Read a date value from the parcel at the current dataPosition().
 */
fun Parcel.readDate(): Date? = readNullable { Date(readLong()) }

/**
 * Read a parcelable typed object value from the parcel at the current dataPosition().
 */
fun <T : Parcelable> Parcel.readTypedObjectCompat(
    c: Parcelable.Creator<T>,
) = readNullable { c.createFromParcel(this) }

/**
 * Write a parcelable typed object value into the parcel at the current dataPosition(),
 * growing dataCapacity() if needed.
 */
fun <T : Parcelable> Parcel.writeTypedObjectCompat(
    value: T?,
    flags: Int,
) = writeNullable(value) { it.writeToParcel(this, flags) }

/**
 * Read a list of value from the parcel at the current dataPosition().
 */
fun <T : Parcelable> Parcel.readTypedListCompat(c: Parcelable.Creator<T>) = readNullable {
    val result: List<T> = ArrayList()
    readTypedList(result, c)
    result
}

/**
 * Write a list of value into the parcel at the current dataPosition(),
 * growing dataCapacity() if needed.
 */
fun <T : Parcelable> Parcel.writeTypedListCompat(
    value: List<T>?,
) = writeNullable(value) { this.writeTypedList(it) }

// Enum

/**
 * Write a Enum value into the parcel at the current dataPosition(),
 * growing dataCapacity() if needed.
 */
inline fun <reified T : Enum<T>> Parcel.writeEnum(value: T?) = writeInt(value?.ordinal ?: -1)

/**
 * Read a Enum value from the parcel at the current dataPosition().
 */
inline fun <reified T : Enum<T>> Parcel.readEnum() = readInt().let { if (it >= 0) enumValues<T>()[it] else null }

// generic nullable

/**
 * Write a Nullable value into the parcel at the current dataPosition(),
 * growing dataCapacity() if needed.
 */
inline fun <T> Parcel.writeNullable(value: T?, writer: (T) -> Unit) {
    if (value != null) {
        writeByte(1.toByte())
        writer(value)
    } else {
        writeByte(0.toByte())
    }
}

/**
 * Read a Nullable value from the parcel at the current dataPosition().
 */
inline fun <T> Parcel.readNullable(
    reader: () -> T,
) = if (readByte() != 0.toByte()) reader() else null

// Utilities
inline fun <reified T> parcelableCreator(
    crossinline create: (Parcel) -> T,
) = object : Parcelable.Creator<T> {
    override fun createFromParcel(source: Parcel) = create(source)

    override fun newArray(size: Int) = arrayOfNulls<T>(size)
}
