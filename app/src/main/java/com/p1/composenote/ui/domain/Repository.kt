package com.p1.composenote.ui.domain

import com.p1.composenote.ui.data.model.NoteData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAll(): Flow<List<NoteData>>
    fun getNoteById(id: Long): Flow<NoteData>
    suspend fun insertNote(noteData: NoteData)
    suspend fun updateNote(noteData: NoteData)
    suspend fun deleteNoteById(id: Long)
    fun getBookmarkedNotes(): Flow<List<NoteData>>
}