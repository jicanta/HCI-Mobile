package com.example.hci_mobile.api.data

class DataSourceException(
    var code: Int,
    message: String,
) : Exception(message)