package com.p1.composenote.ui.data.dao

import androidx.room.TypeConverter
import java.util.Date

internal class DataConverter {
    @TypeConverter
    internal fun Long?.toDateConverter(): Date? = this?.let { date -> Date(date) }

    @TypeConverter
    internal fun Date?.fromDateConverter(): Long? = this?.time
}
