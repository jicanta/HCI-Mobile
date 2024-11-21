package com.example.hci_mobile.api.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YesNoAnswer(
    val answer: String,
    @SerialName("image")
    val imageUrl: String
)
