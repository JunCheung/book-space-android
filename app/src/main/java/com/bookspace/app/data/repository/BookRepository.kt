package com.bookspace.app.data.repository

//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.bookspace.app.data.api.BookService
//import com.bookspace.app.data.api.model.book.Book
//import javax.inject.Inject

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bookspace.app.data.api.BookService
import com.bookspace.app.data.api.model.book.Book
import javax.inject.Inject

//internal class BookRepository @Inject internal constructor(
//    private val service: BookService,
//){
//
//    suspend fun getBooks(pageNo: Int, pageSize: Int): NetworkResult<PageResult<Book>> =
//        safeApiCall { service.getBooks(pageNo, pageSize) }
//}

internal class BookRepository @Inject internal constructor(
    private val service: BookService,
) {
    fun getBooks() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { BookPagingSource(service) }
    ).flow
}

class BookPagingSource(private val apiService: BookService) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.getBooks(pageNo = nextPageNumber, pageSize = params.loadSize)
            val books = response.data.list

            LoadResult.Page(
                data = books,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (books.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
