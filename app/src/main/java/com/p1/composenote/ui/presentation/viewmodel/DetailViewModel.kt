package com.p1.composenote.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.usecase.GetNoteByIdUseCase
import com.p1.composenote.ui.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel(
    private val noteId: Long,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set
    val isFormNotBlank: Boolean
        get() = state.title.isNotEmpty()
                && state.content.isNotEmpty()
    private val note: NoteData
        get() = state.run {
            NoteData(
                id = id,
                title = title,
                content = content,
                createData = createDate,
                isBookMarked = isBookmark
            )
        }

    private fun initialize() {
        val isUpdatingNote = noteId != -1L
        state = state.copy(isUpdatingNote = isUpdatingNote)
        if (isUpdatingNote) {
            getNoteById()
        }
    }

    private fun getNoteById() =
        viewModelScope.launch {
            getNoteByIdUseCase(noteId).collectLatest { note ->
                state = state.copy(
                    id = note.id,
                    title = note.title,
                    content = note.content,
                    isBookmark = note.isBookMarked,
                    createDate = note.createData
                )
            }
        }

    init {
        initialize()
    }

    fun onTitleChange(title: String) {
        state = state.copy(title = title)
    }

    fun onContentChange(content: String) {
        state = state.copy(content = content)
    }

    fun onBookmarkChange(isBookmark: Boolean) {
        state = state.copy(isBookmark = isBookmark)
    }

    fun addOrUpdateNote() = viewModelScope.launch {
        insertNoteUseCase(noteData = note)
    }

    data class DetailState(
        val id: Long = 0,
        val title: String = "",
        val content: String = "",
        val isBookmark: Boolean = false,
        val createDate: Date = Date(),
        val isUpdatingNote: Boolean = false,

        )


    sealed class DetailScreenState {
        data object Loading : DetailScreenState()
        data class Success(val notes: NoteData, val isUpdatingNote: Boolean) : DetailScreenState()
        data class Error(val error: String) : DetailScreenState()
    }
}