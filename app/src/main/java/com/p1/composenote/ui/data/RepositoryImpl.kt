package com.p1.composenote.ui.data

import com.p1.composenote.ui.data.dao.NoteDao
import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.Repository
import kotlinx.coroutines.flow.Flow

internal class RepositoryImpl(private val noteDao: NoteDao) : Repository {

    override fun getAll(): Flow<List<NoteData>> = noteDao.getAll()

    override fun getNoteById(id: Long): Flow<NoteData> = noteDao.getNoteById(id = id)

    override suspend fun insertNote(noteData: NoteData) = noteDao.insertNote(noteData = noteData)

    override suspend fun updateNote(noteData: NoteData) = noteDao.updateNote(noteData = noteData)

    override suspend fun deleteNoteById(id: Long) = noteDao.delete(id = id)

    override fun getBookmarkedNotes(): Flow<List<NoteData>> = noteDao.getBookmarkedNotes()

}