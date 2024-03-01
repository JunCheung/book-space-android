package com.bookspace.app.data.api

import com.bookspace.app.data.api.model.ApiResponse
import com.bookspace.app.data.api.model.PageResult
import com.bookspace.app.data.api.model.book.Book
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {

    @POST("/book/create")
    suspend fun createBook(@Body request: Book): ApiResponse<String>

    @GET("/book/get/{id}")
    suspend fun getBook(@Path("id") id: String): ApiResponse<Book>

    @PUT("/book/update")
    suspend fun updateBook(@Body request: Book): ApiResponse<Boolean>

    @GET("/book/page")
    suspend fun getBooks(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<PageResult<Book>>

    @DELETE("/book/delete/{id}")
    suspend fun deleteBook(@Path("id") id: String): ApiResponse<Boolean>
}