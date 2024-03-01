package com.bookspace.app.ui.book

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bookspace.app.data.api.BookService
import com.bookspace.app.data.api.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditBookViewModel @Inject internal constructor(
    private val bookService: BookService
) : ViewModel() {

    private val _bookInput = mutableStateOf(Book.new())
    val bookInput: State<Book> = _bookInput

    fun loadBook(bookId: String) {
        isLoading.value = true
        viewModelScope.launch {
            val response = bookService.getBook(bookId)
            if (response.isSuccess()) {
                _bookInput.value = response.data
            } else {
                errorMessage.value = "Error: ${response.getResponseMsg()}"
            }
            isLoading.value = false
        }
    }

    val isLoading = mutableStateOf(false)

    val errorMessage = MutableLiveData<String>()

    fun onNameChanged(name: String) {
        _bookInput.value = _bookInput.value.copy(name = name)
    }

    fun onAuthorChanged(author: String) {
        _bookInput.value = _bookInput.value.copy(author = author)
    }

    fun onIsbnChanged(isbn: String) {
        _bookInput.value = _bookInput.value.copy(isbn = isbn)
    }

    fun onPublicationDateChanged(publicationDate: String) {
        _bookInput.value = _bookInput.value.copy(publication = publicationDate)
    }

    fun deleteBook(bookId: String, navController: NavController) {
        viewModelScope.launch {
            bookService.deleteBook(bookId)
            navController.popBackStack()
        }
    }

    fun saveBook(navController: NavController) {
        val input = _bookInput.value
        if (input.name.isNotEmpty()) {
            isLoading.value = true
            viewModelScope.launch {
                val response = if (input.id != null) {
                    bookService.updateBook(input)
                } else {
                    bookService.createBook(input)
                }
                if (response.isSuccess()) {
                    navController.popBackStack()
                } else {
                    errorMessage.value = "Error: ${response.getResponseMsg()}"
                }
                isLoading.value = false
            }
        } else {
            errorMessage.value = "Please enter book name"
        }
    }
}