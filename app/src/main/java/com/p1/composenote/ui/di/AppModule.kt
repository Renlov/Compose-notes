package com.p1.composenote.ui.di

import androidx.room.Room
import com.p1.composenote.ui.data.RepositoryImpl
import com.p1.composenote.ui.data.dao.DATABASE_NAME
import com.p1.composenote.ui.data.dao.NoteDataBase
import com.p1.composenote.ui.domain.Repository
import com.p1.composenote.ui.domain.usecase.DeleteNoteByIdUseCase
import com.p1.composenote.ui.domain.usecase.GetAllNotesUseCase
import com.p1.composenote.ui.domain.usecase.GetBookmarkedNotesUseCase
import com.p1.composenote.ui.domain.usecase.GetNoteByIdUseCase
import com.p1.composenote.ui.domain.usecase.InsertNoteUseCase
import com.p1.composenote.ui.domain.usecase.UpdateNoteUseCase
import com.p1.composenote.ui.presentation.viewmodel.BookmarkViewModel
import com.p1.composenote.ui.presentation.viewmodel.HomeScreenViewModel
import com.p1.composenote.ui.presentation.viewmodel.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf

val appModule = module {
    single { Room.databaseBuilder(androidContext(), NoteDataBase::class.java, DATABASE_NAME).build() }

    single { get<NoteDataBase>().noteDao() }

    single<Repository> { RepositoryImpl(get()) }

    factoryOf(::DeleteNoteByIdUseCase)
    factoryOf(::GetBookmarkedNotesUseCase)
    factoryOf(::GetAllNotesUseCase)
    factoryOf(::GetNoteByIdUseCase)
    factoryOf(::InsertNoteUseCase)
    factoryOf(::UpdateNoteUseCase)

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::BookmarkViewModel)
}