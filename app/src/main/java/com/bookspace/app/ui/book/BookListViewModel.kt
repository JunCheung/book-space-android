package com.bookspace.app.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bookspace.app.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class BookListViewModel @Inject internal constructor(
    repository: BookRepository
) : ViewModel() {

    val bookList = repository.getBooks().cachedIn(viewModelScope)

}