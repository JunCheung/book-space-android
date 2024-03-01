package com.bookspace.app.data.api.model

data class ApiResponse<T>(val code: Int, val msg: String, val data: T) : BaseResponse<T>() {

    override fun isSuccess() = code == 0

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg

}