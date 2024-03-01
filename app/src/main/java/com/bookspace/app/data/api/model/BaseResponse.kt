package com.bookspace.app.data.api.model

/**
 * Base Response for all the API responses
 */
abstract class BaseResponse<T> {

    abstract fun isSuccess(): Boolean

    abstract fun getResponseData(): T

    abstract fun getResponseCode(): Int

    abstract fun getResponseMsg(): String

}