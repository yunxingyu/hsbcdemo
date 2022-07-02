package com.hsbc.hsbcdemo.ui.home

import com.hsbc.hsbcdemo.data.model.ListVideo


data class HomeResult(
    val success: ListVideo? = null,
    val error: Int? = null
)