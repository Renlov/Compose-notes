package com.p1.composenote.ui.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.p1.composenote.ui.data.model.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM noteData ORDER BY createData")
    fun getAll(): Flow<List<NoteData>>

    @Query("SELECT * FROM noteData WHERE id=:id ORDER BY createData")
    fun getNoteById(id: Long): Flow<NoteData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteData: NoteData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(noteData: NoteData)

    @Query("DELETE FROM noteData WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM noteData WHERE isBookMarked = 1 ORDER BY createData DESC")
    fun getBookmarkedNotes(): Flow<List<NoteData>>
}