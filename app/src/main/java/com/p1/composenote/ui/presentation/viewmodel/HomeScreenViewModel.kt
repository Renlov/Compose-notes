package com.p1.composenote.ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.domain.usecase.DeleteNoteByIdUseCase
import com.p1.composenote.ui.domain.usecase.GetAllNotesUseCase
import com.p1.composenote.ui.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
): ViewModel() {

    private val homeScreenState: MutableStateFlow<HomeScreenState> = MutableStateFlow(
        HomeScreenState.Loading
    )

    init {
        getAllNotes()
    }

    fun getHomeScreenState(): Flow<HomeScreenState> = homeScreenState

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteByIdUseCase(noteId = noteId)
        }
    }

    fun onBookMarkChange(note: NoteData) {
        viewModelScope.launch {
            updateNoteUseCase(note.copy(isBookMarked = !note.isBookMarked))
        }
    }

    private fun getAllNotes() {
        getAllNotesUseCase().onEach { note ->
            homeScreenState.emit(HomeScreenState.Success(note))
        }.catch { throwable ->
            homeScreenState.emit(HomeScreenState.Error(throwable.message ?: "unknown error"))
        }.launchIn(viewModelScope)
    }

    sealed class HomeScreenState {
        data object Loading : HomeScreenState()
        data class Success(val noteData: List<NoteData>): HomeScreenState()
        data class Error(val message: String) : HomeScreenState()
    }
}