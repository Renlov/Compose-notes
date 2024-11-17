package com.p1.composenote.ui.domain.usecase

import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetNoteByIdUseCase(private val repository: Repository) {
    operator fun invoke(id: Long): Flow<NoteData> = repository.getNoteById(id = id)
}