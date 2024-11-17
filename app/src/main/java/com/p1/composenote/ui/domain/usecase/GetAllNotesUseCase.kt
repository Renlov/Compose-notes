package com.p1.composenote.ui.domain.usecase

import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<List<NoteData>> = repository.getAll()
}