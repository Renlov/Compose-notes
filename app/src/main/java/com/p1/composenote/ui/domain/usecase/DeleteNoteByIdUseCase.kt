package com.p1.composenote.ui.domain.usecase

import com.p1.composenote.ui.domain.Repository

class DeleteNoteByIdUseCase(private val repository: Repository) {
    suspend operator fun invoke(noteId: Long) {
        repository.deleteNoteById(id = noteId)
    }
}