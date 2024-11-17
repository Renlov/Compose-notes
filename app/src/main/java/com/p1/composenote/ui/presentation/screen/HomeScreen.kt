package com.p1.composenote.ui.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.p1.composenote.ui.data.model.NoteData
import com.p1.composenote.ui.presentation.mock.notes
import com.p1.composenote.ui.presentation.viewmodel.HomeScreenViewModel

@Preview(showSystemUi = true)
@Composable
fun PrevScreen() {
    HomeScreen(
        modifier = Modifier,
        state = HomeScreenViewModel.HomeScreenState.Success(notes),
        onBookmarkChange = {

        },
        onDeleteNote = {

        },
        onNoteClicked = {

        }
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenViewModel.HomeScreenState,
    onBookmarkChange: (noteData: NoteData) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    when (state) {
        is HomeScreenViewModel.HomeScreenState.Error -> {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )
        }
        HomeScreenViewModel.HomeScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is HomeScreenViewModel.HomeScreenState.Success -> {
            HomeDetail(
                noteData = state.noteData,
                modifier = modifier,
                onBookmarkChange = onBookmarkChange,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked
            )
        }
    }
}

@Composable
private fun HomeDetail(
    noteData: List<NoteData>,
    modifier: Modifier,
    onBookmarkChange: (noteData: NoteData) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {
        itemsIndexed(noteData) { index, noteData ->
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

@Composable
fun NoteCard(
    index: Int,
    noteData: NoteData,
    onBookmarkChange: (noteData: NoteData) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    val isEvenIndex = index % 2 == 0
    val shape = when {
        isEvenIndex -> {
            RoundedCornerShape(
                topStart = 50f,
                bottomEnd = 50f
            )
        }
        else -> {
            RoundedCornerShape(
                topEnd = 50f,
                bottomStart = 50f
            )
        }
    }
    val icon = if (noteData.isBookMarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        shape = shape,
        onClick = {
            onNoteClicked(noteData.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = noteData.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size((4.dp)))
            Text(
                text = noteData.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onDeleteNote(noteData.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                IconButton(
                    onClick = {
                        onBookmarkChange(noteData)
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Bookmark"
                    )
                }
            }
        }
    }
}