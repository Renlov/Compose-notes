package com.p1.composenote.ui.presentation.mock

import com.p1.composenote.ui.data.model.NoteData
import java.util.Date

val placeHolderText =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas porttitor nunc vel metus mollis suscipit. Phasellus nec eros id ex aliquam scelerisque. Phasellus quis feugiat eros. Nam sodales ante ac lorem convallis tempus. Sed lacinia consequat diam at ultrices. Nullam lacinia dignissim aliquam. Proin sit amet quam efficitur, euismod nunc eu, aliquam orci. Ut mattis orci a purus ultricies sodales. Pellentesque odio quam, aliquet nec accumsan et, pharetra et lacus. Pellentesque faucibus, dolor quis iaculis fringilla, ligula nisl imperdiet massa, vel volutpat velit elit ac magna. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus pharetra dolor nec magna condimentum volutpat. "

val notes = listOf(
    NoteData(
        id = 0,
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        isBookMarked = false,
        createData = Date()
    ),
    NoteData(
        id = 1,
        title = "JetPack Compose",
        content = "Testing",
        createData = Date(),
        isBookMarked = true,

        ),
    NoteData(
        id = 2,
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        isBookMarked = false,
        createData = Date()
    ),
    NoteData(
        id = 3,
        title = "JetPack Compose",
        content = placeHolderText,
        createData = Date(),
        isBookMarked = true,

        ),
    NoteData(
        id = 4,
        title = "Room Database",
        content = placeHolderText,
        isBookMarked = false,
        createData = Date()
    ),
    NoteData(
        id = 5,
        title = "JetPack Compose",
        content = placeHolderText + placeHolderText,
        createData = Date(),
        isBookMarked = true,
    ),
)