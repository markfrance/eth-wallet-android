package com.myetherwallet.mewconnect.content.data

import com.myetherwallet.mewconnect.content.gson.JsonParser

open class BaseMessage {
    fun toByteArray() = JsonParser.toJson(this).toByteArray()
}