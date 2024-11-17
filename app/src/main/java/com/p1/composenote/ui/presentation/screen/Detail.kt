package com.p1.composenote.ui.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.p1.composenote.ui.presentation.mock.notes
import com.p1.composenote.ui.presentation.viewmodel.DetailViewModel
import com.p1.composenote.ui.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Preview(showSystemUi = true)
@Composable
fun PrevScreenDetail() {
    DetailScreen(
        isUpdateNote = false,
        title = "title",
        content = "content",
        isBookmark = false,
        isFormatNotBlank = true,
        onBookmarkChange = {},
        onTitleChange = {},
        onContentChange = {},
        onButtonClick = {},
        onNavigateClick = {}
    )
}


@Composable
fun DetailScreenMain(
    noteId: Long,
    navigateUp: () -> Unit
) {
    val viewModel = koinViewModel<DetailViewModel>() {
        parametersOf(noteId)
    }
    val state = viewModel.state
    DetailScreen(
        isUpdateNote = state.isUpdatingNote,
        isFormatNotBlank = viewModel.isFormNotBlank,
        title = state.title,
        content = state.content,
        isBookmark = state.isBookmark,
        onBookmarkChange = viewModel::onBookmarkChange,
        onContentChange = viewModel::onContentChange,
        onTitleChange = viewModel::onTitleChange,
        onButtonClick = {
            viewModel.addOrUpdateNote()
            navigateUp()
        },
        onNavigateClick = navigateUp
    )
}

@Composable
private fun DetailScreen(
    isUpdateNote: Boolean,
    title: String,
    content: String,
    isBookmark: Boolean,
    isFormatNotBlank: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    onNavigateClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection(
            title = title,
            isBookmark = isBookmark,
            onBookmarkChange = onBookmarkChange,
            onTitleChange = onTitleChange,
            onNavigate = onNavigateClick,
            isFormatNotBlank = isFormatNotBlank,
            onButtonClick = onButtonClick,
            isUpdateNote = isUpdateNote
        )
        Spacer(modifier = Modifier.Companion.size(12.dp))
        Spacer(modifier = Modifier.Companion.size(12.dp))
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            label = "Content",
            onValueChange = onContentChange
        )
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookmark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onNavigate: () -> Unit,
    onButtonClick: () -> Unit,
    isFormatNotBlank: Boolean,
    isUpdateNote: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigate) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = title,
            label = "Title",
            labelAlign = TextAlign.Center,
            onValueChange = onTitleChange
        )
        IconButton(onClick = { onBookmarkChange(!isBookmark) }) {
            val icon = if (isBookmark) Icons.Default.Favorite else Icons.Default.FavoriteBorder
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
        AnimatedVisibility(isFormatNotBlank) {
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onButtonClick) {
                    val icon = if (isUpdateNote) Icons.Default.Info else Icons.Default.Check
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun NotesTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelAlign: TextAlign? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlign,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}

