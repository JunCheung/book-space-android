package com.bookspace.app.data.api.model.book

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    @Json(name = "id")
    var id: String?,
    @Json(name = "name")
    var name: String,
    @Json(name = "cover")
    var cover: String?,
    @Json(name = "author")
    var author: String?,

    @Json(name = "publication")
    var publication: String?,

    @Json(name = "isbn")
    var isbn: String?,
) {
    companion object {
        fun sample(): Book {
            return Book(
                "1",
                "Book 1",
                "https://images-na.ssl-images-amazon.com/images/I/51Gx6ZzJ3NL._SX331_BO1,204,203,200_.jpg",
                "Author 1",
                "Publication 1",
                "ISBN 1"
            )
        }

        fun new(): Book {
            return Book(
                null,
                "",
                "",
                "",
                "",
                ""
            )
        }
    }
}