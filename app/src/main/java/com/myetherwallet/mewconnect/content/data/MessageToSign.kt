package com.myetherwallet.mewconnect.content.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageToSign(
        @SerializedName("hash")
        val hash: String,
        @SerializedName("text")
        val text: String
) : Parcelable