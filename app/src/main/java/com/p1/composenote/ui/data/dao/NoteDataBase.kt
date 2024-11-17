package com.p1.composenote.ui.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.p1.composenote.ui.data.model.NoteData

internal const val DATABASE_NAME = "NoteDataBase_NAME"

@TypeConverters(value = [DataConverter::class])
@Database(
    entities = [NoteData::class],
    version = 1,
    exportSchema = true
)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}