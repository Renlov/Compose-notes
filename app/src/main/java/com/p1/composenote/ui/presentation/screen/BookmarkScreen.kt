package com.p1.composenote.ui.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.presentation.mock.notes
import com.p1.composenote.ui.presentation.viewmodel.BookmarkViewModel

@Preview(showSystemUi = true)
@Composable
fun PrevScreenBookMark() {
    BookmarkScreen(
        modifier = Modifier,
        state = BookmarkViewModel.BookmarkScreenState.Success(notes),
        onBookmarkChange = {

        },
        onDeleteNote = {

        },
        onNoteClicked = {

        }
    )
}

@Composable
fun BookmarkScreen(
    state: BookmarkViewModel.BookmarkScreenState,
    modifier: Modifier,
    onBookmarkChange: (noteData: NoteData) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    when(state) {
        is BookmarkViewModel.BookmarkScreenState.Error -> {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )
        }
        BookmarkViewModel.BookmarkScreenState.Loading -> {
            CircularProgressIndicator()
        }
        is BookmarkViewModel.BookmarkScreenState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(4.dp)
            ) {
                itemsIndexed(state.notes) { index, noteData ->
                    NoteCard(
                        index = index,
                        noteData = noteData,
                        onBookmarkChange = onBookmarkChange,
                        onDeleteNote = onDeleteNote,
                        onNoteClicked = onNoteClicked
                    )
                }
            }
        }
    }
}