package com.p1.composenote.ui.domain.usecase

import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.Repository

class UpdateNoteUseCase(private val repository: Repository) {
    suspend operator fun invoke(noteData: NoteData) {
        repository.updateNote(noteData = noteData)
    }
}