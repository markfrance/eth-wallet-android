package com.myetherwallet.mewconnect.content.data

import com.google.gson.annotations.SerializedName

data class MessageSignData(
        @SerializedName("address")
        private val address: String,
        @SerializedName("sig")
        private val signature: String
)