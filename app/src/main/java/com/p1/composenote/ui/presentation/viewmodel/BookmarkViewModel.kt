package com.p1.composenote.ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.usecase.DeleteNoteByIdUseCase
import com.p1.composenote.ui.domain.usecase.GetBookmarkedNotesUseCase
import com.p1.composenote.ui.domain.usecase.UpdateNoteUseCase
import com.p1.composenote.ui.presentation.mock.notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getBookmarkedNotesUseCase: GetBookmarkedNotesUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase
) : ViewModel() {

    private val bookmarkScreenState: MutableStateFlow<BookmarkScreenState> = MutableStateFlow(BookmarkScreenState.Loading)

    init {
        getAllBookmarkNotes()
    }

    fun getBookmarkScreenState(): Flow<BookmarkScreenState> = bookmarkScreenState

    fun onBookmarkChange(note: NoteData) {
        viewModelScope.launch {
            updateNoteUseCase(note.copy(isBookMarked = !note.isBookMarked))
        }
    }

    fun deleteNotes(noteId: Long) {
        viewModelScope.launch {
            deleteNoteByIdUseCase(noteId = noteId)
        }
    }

    private fun getAllBookmarkNotes() {
        getBookmarkedNotesUseCase().onEach { notes ->
            bookmarkScreenState.emit(BookmarkScreenState.Success(notes))
        }.catch { throwable ->
            bookmarkScreenState.emit(BookmarkScreenState.Error(throwable.message ?: "Unknown error"))
        }.launchIn(viewModelScope)
    }

    sealed class BookmarkScreenState {
        data object Loading : BookmarkScreenState()
        data class Success(val notes: List<NoteData>) : BookmarkScreenState()
        data class Error(val error: String) : BookmarkScreenState()
    }
}